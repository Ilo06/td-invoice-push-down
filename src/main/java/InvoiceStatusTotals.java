public class InvoiceStatusTotals {

    private Double totalPaid;
    private Double totalConfirmed;
    private Double totalDraft;

    public InvoiceStatusTotals(Double totalPaid,
                               Double totalConfirmed,
                               Double totalDraft) {
        this.totalPaid = totalPaid;
        this.totalConfirmed = totalConfirmed;
        this.totalDraft = totalDraft;
    }

    @Override
    public String toString() {
        return """
                total_paid = %s
                total_confirmed = %s
                total_draft = %s
                """.formatted(totalPaid, totalConfirmed, totalDraft);
    }
}
