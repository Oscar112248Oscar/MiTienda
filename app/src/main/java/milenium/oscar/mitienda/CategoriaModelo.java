package milenium.oscar.mitienda;

import java.io.Serializable;

public class CategoriaModelo implements Serializable {

    private  String iconoCategoriaLink;
    private String categoriaNombre;

    public CategoriaModelo() {
    }

    public CategoriaModelo(String iconoCategoriaLink, String categoriaNombre) {
        this.iconoCategoriaLink = iconoCategoriaLink;
        this.categoriaNombre = categoriaNombre;
    }

    public String getIconoCategoriaLink() {
        return iconoCategoriaLink;
    }

    public void setIconoCategoriaLink(String iconoCategoriaLink) {
        this.iconoCategoriaLink = iconoCategoriaLink;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }
}
