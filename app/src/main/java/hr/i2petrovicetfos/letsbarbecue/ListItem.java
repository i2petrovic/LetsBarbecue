package hr.i2petrovicetfos.letsbarbecue;

//kreiranje objekta za popunjavanje pojedinog retka listview-a iz razloga što se radi o custom
//Listview-u kojem se predaju 3 vrijednosti u 3 polja
public class ListItem {
    private String listItem;
    private Double cijena;
    private String jedinica;

    public ListItem(String listItem, Double cijena, String jedinica) {
        this.listItem = listItem;
        this.cijena = cijena;
        this.jedinica = jedinica;
    }

    public ListItem() {
    }

    public String getListItem() {
        return listItem;
    }

    public void setListItem(String listItem) {
        this.listItem = listItem;
    }

    public Double getCijena() {
        return cijena;
    }

    public void setCijena(Double cijena) {
        this.cijena = cijena;
    }

    public String getJedinica() {
        return jedinica;
    }

    public void setJedinica(String jedinica) {
        this.jedinica = jedinica;
    }
}
