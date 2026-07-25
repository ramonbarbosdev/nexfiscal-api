package com.nexfiscal_api.seed;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexfiscal_api.model.Papel;
import com.nexfiscal_api.model.Usuario;
import com.nexfiscal_api.model.UsuarioPapel;
import com.nexfiscal_api.repository.PapelRepository;
import com.nexfiscal_api.repository.UsuarioPapelRepository;
import com.nexfiscal_api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminSeedRunner implements ApplicationRunner {

    private final UsuarioRepository usuarioRepository;
    private final PapelRepository papelRepository;
    private final UsuarioPapelRepository usuarioPapelRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (usuarioRepository.findByNmEmailIgnoreCaseAndFlAtivoTrue(adminEmail).isPresent()) {
            return;
        }

        Usuario admin = new Usuario();
        admin.setNmEmail(adminEmail);
        admin.setNmUsuario("Administrador");
        admin.setDsSenha(passwordEncoder.encode(adminPassword));
        admin.setFlAtivo(true);
        admin = usuarioRepository.save(admin);

        Papel papelAdmin = papelRepository.findByNmPapelIgnoreCase("ADMIN")
                .orElseThrow(() -> new IllegalStateException("Papel ADMIN não encontrado. Verifique as migrations."));

        if (!usuarioPapelRepository.existsByUsuarioIdUsuarioAndPapelNmPapelIgnoreCase(
                admin.getIdUsuario(), "ADMIN")) {
            UsuarioPapel vinculo = new UsuarioPapel();
            vinculo.setUsuario(admin);
            vinculo.setPapel(papelAdmin);
            usuarioPapelRepository.save(vinculo);
        }
    }
}
