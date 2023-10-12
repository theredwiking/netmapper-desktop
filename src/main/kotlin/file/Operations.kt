package file

import network.ServerInfo
import network.Vlan
import java.io.*
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

data class SaveData(val networks: List<Vlan>, val servers: List<ServerInfo>): Serializable

fun write(path: String, data: SaveData): Boolean {
    try {
        val file = FileOutputStream(path);
        val stream = ObjectOutputStream(file);
        stream.writeObject(data);
        stream.close();
        file.close();
        return true
    } catch (e: Exception) {
        println(e);
        return false;
    }
}

fun read(path: String, onData: (SaveData ) -> Unit): Boolean {
    try {
        val file = FileInputStream(path);
        val stream = ObjectInputStream(file);
        val data = stream.readObject() as SaveData
        stream.close();
        file.close();
        onData(data);
        return true;
    } catch(e: Exception) {
        println(e);
        onData(SaveData(emptyList(), emptyList()));
        return false;
    }
}

fun filePicker(): String {
    try {
        val filePick = JFileChooser();
        filePick.dialogTitle = "Choose nsf file";
        filePick.fileFilter = FileNameExtensionFilter("NMF File", "nmf");
        val r: Int = filePick.showOpenDialog(null);
        if(r == JFileChooser.APPROVE_OPTION) {
            return filePick.selectedFile.toString();
        } else {
            filePick.isVisible = false;
            return "";
        }
    } catch (e: Exception) {
        println(e)
        return "";
    }
}

fun dirPicker(): String {
    try {
        val filePick = JFileChooser();
        filePick.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        val r: Int = filePick.showOpenDialog(null)
        if(r == JFileChooser.APPROVE_OPTION) {
            return filePick.selectedFile.toString();
        } else {
            return "";
        }
    } catch (e: Exception) {
        println(e)
        return ""
    }
}