import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancodeDados extends SQLiteOpenHelper {
    private static BancodeDados instance = null;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BancoInteracao.sqlite";

    private Context context;

    public BancodeDados(Context context) {
        super(context, DATABASE_NAME , null , DATABASE_VERSION);
    }
    public static BancodeDados Sharedinstance(Context context){
        if (instance == null){
            instance = new BancodeDados(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1;
        query1 = "CREATE TABLE IF NOT EXISTS Cliente (IdCli INTEGER PRIMARY KEY AUTOINCREMENT, NomeCli TEXT NOT NULL, Telefone TEXT)";
        db.execSQL(query1);

        String query2;
        query2 = "CREATE TABLE IF NOT EXISTS ContaLanchonete (IdConta INTEGER PRIMARY KEY AUTOINCREMENT, DataAbertura TEXT DEFAULT (datetime('now')), valorTotal REAL NOT NULL,FOREIGN KEY (IdCli) REFERENCES Cliente(IdCli))";
        db.execSQL(query2);

        String query3;
        query3 = "CREATE TABLE IF NOT EXISTS ItensConta (Iditem INTEGER PRIMARY KEY AUTOINCREMENT, Descricao TEXT NOT NULL, Quantidade INTEGER NOT NULL, PrecoUni REAL NOT NULL, FOREIGN KEY (IdConta) REFERENCES  ContaLanchonete(IdConta))";
        db.execSQL(query3);
    }
    public void insertCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("IdCliente", cliente.getIdCli());
        values.put("NomeCLiente", cliente.getNomeCli());
        values.put("Telefone", cliente.getTelefone());

        db.insert("Cliente", "" ,values );

        db.close();
    }

    public void insertContaLanchonete(ContaLanchonete contalanchonete) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("IdConta", contalanchonete.getIdConta());
        values.put("dataAbertura", contalanchonete.getDataAbertura());
        values.put("valorTotal", contalanchonete.getValorTotal());

        db.insert("ContaLanchonete", "" ,values );

        db.close();
    }

    public void insertIensConta(ItensConta itensConta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("IdItem", itensConta.getIdItem());
        values.put("descricao", itensConta.getDescricao());
        values.put("quantidade", itensConta.getQuantidade());
        values.put("precoUnico", itensConta.getPrecoUni());

        db.insert("ItensConta", "",values);

        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}




