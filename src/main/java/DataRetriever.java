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
}
