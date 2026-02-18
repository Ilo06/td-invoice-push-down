import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {

    public List<InvoiceTotal> findInvoiceTotals() {

        String sql = """
                SELECT i.id,
                       i.customer_name,
                       i.status,
                       SUM(il.quantity * il.unit_price) AS total
                FROM invoice i
                JOIN invoice_line il ON il.invoice_id = i.id
                GROUP BY i.id, i.customer_name, i.status
                ORDER BY i.id
                """;

        List<InvoiceTotal> result = new ArrayList<>();

        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("customer_name");
                InvoiceStatus status = InvoiceStatus.valueOf(rs.getString("status"));
                Double total = rs.getDouble("total");

                result.add(new InvoiceTotal(id, name,status, total));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public List<InvoiceTotal> findConfirmedAndPaidInvoiceTotals() {

        String sql = """
                SELECT i.id,
                       i.customer_name,
                       i.status,
                       SUM(il.quantity * il.unit_price) AS total
                FROM invoice i
                JOIN invoice_line il ON il.invoice_id = i.id
                WHERE i.status IN ('CONFIRMED', 'PAID')
                GROUP BY i.id, i.customer_name, i.status
                ORDER BY i.id
                """;

        List<InvoiceTotal> result = new ArrayList<>();

        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("customer_name");
                InvoiceStatus status = InvoiceStatus.valueOf(rs.getString("status"));
                Double total = rs.getDouble("total");

                result.add(new InvoiceTotal(id, name, status, total));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public InvoiceStatusTotals computeStatusTotals() {

        String sql = """
                SELECT
                    SUM(CASE WHEN i.status = 'PAID' THEN il.quantity * il.unit_price ELSE 0 END) AS total_paid,
                    SUM(CASE WHEN i.status = 'CONFIRMED' THEN il.quantity * il.unit_price ELSE 0 END) AS total_confirmed,
                    SUM(CASE WHEN i.status = 'DRAFT' THEN il.quantity * il.unit_price ELSE 0 END) AS total_draft
                FROM invoice i
                JOIN invoice_line il ON il.invoice_id = i.id
                """;

        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                Double paid = rs.getDouble("total_paid");
                Double confirmed = rs.getDouble("total_confirmed");
                Double draft = rs.getDouble("total_draft");

                return new InvoiceStatusTotals(paid, confirmed, draft);
            }

            return new InvoiceStatusTotals(0.0d, 0.0d, 0.0d);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Double computeWeightedTurnover() {

        String sql = """
                SELECT SUM(
                    CASE
                        WHEN i.status = 'PAID' THEN il.quantity * il.unit_price * 1
                        WHEN i.status = 'CONFIRMED' THEN il.quantity * il.unit_price * 0.5
                        ELSE 0
                    END
                ) AS weighted_total
                FROM invoice i
                JOIN invoice_line il ON il.invoice_id = i.id
                """;

        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("weighted_total");
            }

            return 0.0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<InvoiceTaxSummary> findInvoiceTaxSummaries() {

        String sql = """
                SELECT
                    i.id,
                    SUM(il.quantity * il.unit_price) AS total_ht,
                
                    SUM(il.quantity * il.unit_price)
                        * (SELECT rate FROM tax_config LIMIT 1) / 100 AS total_tva,
                
                    SUM(il.quantity * il.unit_price)
                        * (1 + (SELECT rate FROM tax_config LIMIT 1) / 100) AS total_ttc
                
                FROM invoice i
                JOIN invoice_line il ON il.invoice_id = i.id
                GROUP BY i.id
                ORDER BY i.id;
                
                """;

        List<InvoiceTaxSummary> result = new ArrayList<>();

        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                int id = rs.getInt("id");
                Double ht = rs.getDouble("total_ht");
                Double tva = rs.getDouble("total_tva");
                Double ttc = rs.getDouble("total_ttc");

                result.add(new InvoiceTaxSummary(id, ht, tva, ttc));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}


