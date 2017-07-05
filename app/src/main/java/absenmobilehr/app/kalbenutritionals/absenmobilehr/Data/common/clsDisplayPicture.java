package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Robert on 16/06/2017.
 */
@DatabaseTable
public class clsDisplayPicture implements Serializable {
    @DatabaseField(columnName = "id")
    public int id;

    @DatabaseField(columnName = "image", dataType = DataType.BYTE_ARRAY)
    public byte[] image;

    public clsDisplayPicture(int id, byte[] image){
        this.id = id;
        this.image = image;
    }

    public clsDisplayPicture(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
