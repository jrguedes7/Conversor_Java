import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class JotaErre {

    private static final String REAL_BRL_LABEL = "Real (BRL)";
    private static final String DOLLAR_USD_LABEL = "Dólar dos Estados Unidos (USD)";
    private static final String EURO_EUR_LABEL = "Euro (EUR)";
    private static final String POUND_GBP_LABEL = "Libra esterlina (GBP)";
    private static final String YEN_JPY_LABEL = "Iene japonês (JPY)";
    private static final String FRANCO_SFR_LABEL = "Franco suíço (CHF)";

    private static JTextField valueField;
    private static JTextField dateField;
    private static JComboBox<String> fromCurrencyBox;
    private static JComboBox<String> toCurrencyBox;
    private static JButton convertButton;
    private static JTextField resultField;

    public static void main(String[] args) {
        JFrame frame = new JFrame("JotaErre");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Centraliza a janela

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("JotaErre", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 140, 0)); // Cor do título: laranja escuro

        JLabel dateLabel = new JLabel("Data da Cotação (dd/MM/yyyy):");
        dateField = new JTextField(10);

        JLabel fromCurrencyLabel = new JLabel("Moeda de Origem:");
        fromCurrencyBox = new JComboBox<>(new String[]{
            REAL_BRL_LABEL,
            DOLLAR_USD_LABEL,
            EURO_EUR_LABEL,
            POUND_GBP_LABEL,
            YEN_JPY_LABEL,
            FRANCO_SFR_LABEL
        });
        JLabel toCurrencyLabel = new JLabel("Moeda Destino:");
        toCurrencyBox = new JComboBox<>(new String[]{
            REAL_BRL_LABEL,
            DOLLAR_USD_LABEL,
            EURO_EUR_LABEL,
            POUND_GBP_LABEL,
            YEN_JPY_LABEL,
            FRANCO_SFR_LABEL
        });
        JLabel valueLabel = new JLabel("Valor para Conversão:");
        valueField = new JTextField(10);
        convertButton = new JButton("Converter");
        convertButton.setBackground(new Color(30, 144, 255)); // Cor do botão: azul Dodger
        convertButton.setForeground(Color.WHITE); // Cor do texto do botão: branco
        resultField = new JTextField(10);
        resultField.setEditable(false);
        resultField.setBackground(new Color(230, 230, 250)); // Cor de fundo do campo de resultado: lavanda

        JLabel disclaimerLabel = new JLabel("<html><div style='text-align: center;'>Os cálculos são informativos e não substituem as normas cambiais oficiais.<br>Para dias não úteis, a cotação do último dia útil anterior é usada.<br>O Aplicativo não se responsabiliza por atrasos ou imprecisões nas informações.</div></html>", SwingConstants.CENTER);
        disclaimerLabel.setForeground(new Color(128, 0, 0)); // Cor do aviso: marrom

        convertButton.addActionListener(new CurrencyConverterAction());

        panel.add(titleLabel, gbc);
        panel.add(dateLabel, gbc);
        panel.add(dateField, gbc);
        panel.add(fromCurrencyLabel, gbc);
        panel.add(fromCurrencyBox, gbc);
        panel.add(toCurrencyLabel, gbc);
        panel.add(toCurrencyBox, gbc);
        panel.add(valueLabel, gbc);
        panel.add(valueField, gbc);
        panel.add(convertButton, gbc);
        panel.add(resultField, gbc);
        panel.add(disclaimerLabel, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static class CurrencyConverterAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String dateString = dateField.getText();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date = sdf.parse(dateString);
                if (!dateString.isBlank() && isBusinessDay(date)) {
                    String value = valueField.getText();
                    if (!value.isBlank()) {
                        double convertedValue = convertCurrencies(
                            fromCurrencyBox.getSelectedItem().toString(),
                            toCurrencyBox.getSelectedItem().toString(),
                            Double.parseDouble(value)
                        );
                        resultField.setText(String.format("%.2f", convertedValue));
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, insira uma data de cotação válida.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, insira um número válido.");
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, insira uma data no formato dd/MM/yyyy.");
            }
        }
    }

    private static boolean isBusinessDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return !(dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
    }

    private static double convertCurrencies(String fromCurrency, String toCurrency, double amount) {
        double valorConvertido = 0.0;
        switch (fromCurrency) {
            case REAL_BRL_LABEL:
                if (toCurrency.equals(DOLLAR_USD_LABEL)) {
                    valorConvertido = amount * 0.17;
                } else if (toCurrency.equals(EURO_EUR_LABEL)) {
                    valorConvertido = amount * 0.15;
                } else if (toCurrency.equals(POUND_GBP_LABEL)) {
                    valorConvertido = amount * 0.14;
                }
                break;
            case DOLLAR_USD_LABEL:
                if (toCurrency.equals(REAL_BRL_LABEL)) {
                    valorConvertido = amount * 5.86;
                } else if (toCurrency.equals(EURO_EUR_LABEL)) {
                    valorConvertido = amount * 0.85;
                } else if (toCurrency.equals(POUND_GBP_LABEL)) {
                    valorConvertido = amount * 0.76;
                }
                break;
            case EURO_EUR_LABEL:
                if (toCurrency.equals(REAL_BRL_LABEL)) {
                    valorConvertido = amount * 6.66;
                } else if (toCurrency.equals(DOLLAR_USD_LABEL)) {
                    valorConvertido = amount * 1.17;
                } else if (toCurrency.equals(POUND_GBP_LABEL)) {
                    valorConvertido = amount * 0.91;
                }
                break;
            case POUND_GBP_LABEL:
                if (toCurrency.equals(REAL_BRL_LABEL)) {
                    valorConvertido = amount * 7.42;
                } else if (toCurrency.equals(DOLLAR_USD_LABEL)) {
                    valorConvertido = amount * 1.17;
                } else if (toCurrency.equals(EURO_EUR_LABEL)) {
                    valorConvertido = amount * 1.09;
                }
                break;
            default:
                JOptionPane.showMessageDialog(null, "Moeda não suportada.");
                return 0.0;
        }
        return valorConvertido;
    }
}