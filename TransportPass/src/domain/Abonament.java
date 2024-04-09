package domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Abonament extends Entity<Long>{
    private LocalDateTime dataIncepere;
    private LocalDateTime dataExpirare;
    private Long pret;
    private String tip;
    private Client codClient;

    public Abonament(Long id,LocalDateTime dataIncepere, LocalDateTime dataExpirare, Long pret, String tip, Client codClient) {
        this.id = id;
        this.dataIncepere = dataIncepere;
        this.dataExpirare = dataExpirare;
        this.pret = pret;
        this.tip = tip;
        this.codClient = codClient;
    }
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataIncepere() {
        return dataIncepere;
    }

    public void setDataIncepere(LocalDateTime dataIncepere) {
        this.dataIncepere = dataIncepere;
    }

    public LocalDateTime getDataExpirare() {
        return dataExpirare;
    }

    public void setDataExpirare(LocalDateTime dataExpirare) {
        this.dataExpirare = dataExpirare;
    }

    public Long getPret() {
        return pret;
    }

    public void setPret(Long pret) {
        this.pret = pret;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Client getCodClient() {
        return codClient;
    }

    public void setCodClient(Client codClient) {
        this.codClient = codClient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Abonament abonament = (Abonament) o;
        return Objects.equals(dataIncepere, abonament.dataIncepere) && Objects.equals(dataExpirare, abonament.dataExpirare) && Objects.equals(pret, abonament.pret) && Objects.equals(tip, abonament.tip) && Objects.equals(codClient, abonament.codClient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataIncepere, dataExpirare, pret, tip, codClient);
    }

    @Override
    public String toString() {
        return "Abonament{" +
                "dataIncepere=" + dataIncepere +
                ", dataExpirare=" + dataExpirare +
                ", pret=" + pret +
                ", tip='" + tip + '\'' +
                ", codClient=" + codClient +
                '}';
    }
}
