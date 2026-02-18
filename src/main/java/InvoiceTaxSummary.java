
public class InvoiceTaxSummary {

    private int id;
    private Double ht;
    private Double tva;
    private Double ttc;

    public InvoiceTaxSummary(int id, Double ht, Double tva, Double ttc) {
        this.id = id;
        this.ht = ht;
        this.tva = tva;
        this.ttc = ttc;
    }

    @Override
    public String toString() {
        return id + " | HT " + ht + " | TVA " + tva + " | TTC " + ttc;
    }
}
