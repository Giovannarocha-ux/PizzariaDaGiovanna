package com.example.pizzariadagiovanna;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rgTamanhoPizza, rgTipoBorda;
    private Switch switchMolhos, switchAzeitonas;
    private TextInputEditText inputNomeClinete, inputObservacao;
    private Button btnConfirmar, btnLimpar;
    private ProgressBar progressBar;
    private RecyclerView rvSabores;
    private SaboresAdapter saboresAdapter;

    private int numeroPedido = 1830;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rgTamanhoPizza = findViewById(R.id.rgTamanhoPizza);
        rgTipoBorda = findViewById(R.id.rgTipoBorda);
        switchMolhos = findViewById(R.id.switchMolhos);
        switchAzeitonas = findViewById(R.id.switchAzeitonas);
        inputNomeClinete = findViewById(R.id.inputNome);
        inputObservacao = findViewById(R.id.inputObs);
        btnConfirmar = findViewById(R.id.btnConfirmar);
        btnLimpar = findViewById(R.id.btnLimpar);
        progressBar = findViewById(R.id.progressBar);
        rvSabores = findViewById(R.id.rvSabores);

        configurarSaboresRecyclerView();

        btnConfirmar.setOnClickListener(v -> confirmarPedido());
        btnLimpar.setOnClickListener(v -> limparSelecoes());
    }

    private void configurarSaboresRecyclerView() {
        rvSabores.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<Sabor> sabores = criarListaSabores();
        saboresAdapter = new SaboresAdapter(sabores);
        rvSabores.setAdapter(saboresAdapter);
    }

    private List<Sabor> criarListaSabores() {
        List<Sabor> sabores = new ArrayList<>();
        sabores.add(new Sabor("Calabresa", R.drawable.calabresa));
        sabores.add(new Sabor("Quatro Queijos", R.drawable.quatro_queijos));
        sabores.add(new Sabor("Frango com Catupiry", R.drawable.frango_catupiry));
        sabores.add(new Sabor("Portuguesa", R.drawable.portuguesa));
        sabores.add(new Sabor("Marguerita", R.drawable.marguerita));
        sabores.add(new Sabor("Pepperoni", R.drawable.pepperoni));
        sabores.add(new Sabor("Vegana", R.drawable.vegana));
        return sabores;
    }

    private void confirmarPedido() {
        String tamanho = obterTextoRadioButtonSelecionado(rgTamanhoPizza);
        String borda = obterTextoRadioButtonSelecionado(rgTipoBorda);
        String sabor = saboresAdapter.getSaborSelecionado();
        boolean adicionarMolhos = switchMolhos.isChecked();
        boolean adicionarAzeitonas = switchAzeitonas.isChecked();
        String nomeCliente = inputNomeClinete.getText().toString();
        String observacao = inputObservacao.getText().toString();

        if (tamanho == null || borda == null || sabor == null || nomeCliente.length() == 0) {
            Toast.makeText(this, "Por favor, preencha todas as opções.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);

            String mensagem = "Pedido #" + numeroPedido + ":\n"
                    + "Tamanho: " + tamanho + "\n"
                    + "Borda: " + borda + "\n"
                    + "Sabor: " + sabor + "\n"
                    + (adicionarMolhos ? "Com sachês de molho\n" : "")
                    + (adicionarAzeitonas ? "Com azeitonas\n" : "")
                    + "Nome do cliente: " + nomeCliente + "\n"
                    + "Observações: " + observacao + "\n"
                    + "Obrigado por pedir na nossa pizzaria!";

            numeroPedido++;

            new AlertDialog.Builder(this)
                    .setTitle("Pedido Confirmado")
                    .setMessage(mensagem)
                    .setPositiveButton("OK", null)
                    .show();
        }, 2000);
    }

    private String obterTextoRadioButtonSelecionado(RadioGroup radioGroup) {
        int idSelecionado = radioGroup.getCheckedRadioButtonId();
        if (idSelecionado != -1) {
            RadioButton radioButton = findViewById(idSelecionado);
            return radioButton.getText().toString();
        }
        return null;
    }

    private void limparSelecoes() {
        rgTamanhoPizza.clearCheck();
        rgTipoBorda.clearCheck();
        switchMolhos.setChecked(false);
        switchAzeitonas.setChecked(false);
        inputNomeClinete.setText("");
        inputObservacao.setText("");
        saboresAdapter.limparSelecao();
        Toast.makeText(this, "Seleções limpas.", Toast.LENGTH_SHORT).show();
    }
}
