package com.nexfiscal_api.dto.invoice;

import java.util.List;
import java.util.Map;

public record InvoiceImportRequest(List<Map<String, Object>> invoices) {
}
