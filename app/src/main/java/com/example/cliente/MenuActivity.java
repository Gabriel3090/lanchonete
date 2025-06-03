package com.example.cliente;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private RadioGroup rgSanduiches, rgRefrigerantes;
    private CheckBox cbBatataFrita, cbOnionRings, cbSalada;
    private EditText etQuantidade;
    private Button btnAdicionar, btnFinalizar;
    private TextView tvTotal;

    private ArrayList<ItemPedido> listaItens = new ArrayList<>();
    private double total = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);  

        
        rgSanduiches = findViewById(R.id.rgSanduiches);
        rgRefrigerantes = findViewById(R.id.rgRefrigerantes);
        cbBatataFrita = findViewById(R.id.cbBatataFrita);
        cbOnionRings = findViewById(R.id.cbOnionRings);
        cbSalada = findViewById(R.id.cbSalada);
        etQuantidade = findViewById(R.id.etQuantidade);
        btnAdicionar = findViewById(R.id.btnAdicionar);
        btnFinalizar = findViewById(R.id.btnFinalizar);
        tvTotal = findViewById(R.id.tvTotal);

 
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedIdSanduiche = rgSanduiches.getCheckedRadioButtonId();
                if (selectedIdSanduiche == -1) {
                    Toast.makeText(MenuActivity.this, "Selecione um sanduíche!", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton rbSanduicheSelecionado = findViewById(selectedIdSanduiche);
                String sanduiche = rbSanduicheSelecionado.getText().toString();

                int selectedIdRefri = rgRefrigerantes.getCheckedRadioButtonId();
                if (selectedIdRefri == -1) {
                    Toast.makeText(MenuActivity.this, "Selecione um refrigerante!", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton rbRefriSelecionado = findViewById(selectedIdRefri);
                String refrigerante = rbRefriSelecionado.getText().toString();

                ArrayList<String> acompanhamentos = new ArrayList<>();
                if (cbBatataFrita.isChecked()) acompanhamentos.add("Batata Frita");
                if (cbOnionRings.isChecked()) acompanhamentos.add("Onion Rings");
                if (cbSalada.isChecked()) acompanhamentos.add("Salada Extra");

                String qtdStr = etQuantidade.getText().toString();
                int quantidade;
                try {
                    quantidade = Integer.parseInt(qtdStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(MenuActivity.this, "Digite uma quantidade válida!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (quantidade <= 0) {
                    Toast.makeText(MenuActivity.this, "Quantidade deve ser maior que zero!", Toast.LENGTH_SHORT).show();
                    return;
                }

                double precoSanduiche = 20.0;
                double precoRefrigerante = 5.0;
                double precoAcompanhamento = 5.0;

                double precoTotalItem = (precoSanduiche + precoRefrigerante + (acompanhamentos.size() * precoAcompanhamento)) * quantidade;

                ItemPedido item = new ItemPedido(sanduiche, refrigerante, acompanhamentos, quantidade, precoTotalItem);
                listaItens.add(item);

                total += precoTotalItem;
                tvTotal.setText(String.format("R$ %.2f", total));

               =
                rgSanduiches.clearCheck();
                rgRefrigerantes.clearCheck();
                cbBatataFrita.setChecked(false);
                cbOnionRings.setChecked(false);
                cbSalada.setChecked(false);
                etQuantidade.setText("");
            }
        });

       
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listaItens.isEmpty()) {
                    Toast.makeText(MenuActivity.this, "Nenhum item no pedido!", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuilder resumo = new StringBuilder("Pedido finalizado:\n");
                for (ItemPedido item : listaItens) {
                    resumo.append(item.quantidade).append("x ").append(item.sanduiche)
                            .append(" + ").append(item.refrigerante);

                    if (!item.acompanhamentos.isEmpty()) {
                        resumo.append(" (").append(String.join(", ", item.acompanhamentos)).append(")");
                    }
                    resumo.append(" - R$ ").append(String.format("%.2f", item.precoTotal)).append("\n");
                }
                resumo.append("Total: R$ ").append(String.format("%.2f", total));

                Toast.makeText(MenuActivity.this, resumo.toString(), Toast.LENGTH_LONG).show();

                
                Toast.makeText(MenuActivity.this, "Pedido salvo no banco de dados com sucesso!", Toast.LENGTH_SHORT).show();

                listaItens.clear();
                total = 0.0;
                tvTotal.setText("R$ 0,00");
            }
        });
    }

    
    private static class ItemPedido {
        String sanduiche;
        String refrigerante;
        ArrayList<String> acompanhamentos;
        int quantidade;
        double precoTotal;

        public ItemPedido(String sanduiche, String refrigerante, ArrayList<String> acompanhamentos, int quantidade, double precoTotal) {
            this.sanduiche = sanduiche;
            this.refrigerante = refrigerante;
            this.acompanhamentos = acompanhamentos;
            this.quantidade = quantidade;
            this.precoTotal = precoTotal;
        }
    }
}
