public class Main {
    public static void main(String[] args) {
        DataRetriever dataRetriever = new DataRetriever();

//        System.out.println(dataRetriever.findInvoiceTotals());
//        System.out.println(dataRetriever.findConfirmedAndPaidInvoiceTotals());
//        System.out.println(dataRetriever.computeStatusTotals());
//        System.out.println(dataRetriever.computeWeightedTurnover());
        System.out.println(dataRetriever.findInvoiceTaxSummaries());
    }
}
