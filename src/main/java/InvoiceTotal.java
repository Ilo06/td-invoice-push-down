import java.util.Objects;

public class InvoiceTotal {
    private int id;
    private String name;
    private InvoiceStatus status;
    private Double total;

    public InvoiceTotal(int id, String name, InvoiceStatus status, Double total) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.total = total;
    }

    public InvoiceTotal(int id, String name, Double total) {
        this.id = id;
        this.name = name;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {

        if (o == null || getClass() != o.getClass()) return false;
        InvoiceTotal that = (InvoiceTotal) o;
        return id == that.id && Objects.equals(name, that.name) && status == that.status && Objects.equals(total, that.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, total);
    }

    @Override
    public String toString() {
        return "InvoiceTotal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", total=" + total +
                '}';
    }
}

