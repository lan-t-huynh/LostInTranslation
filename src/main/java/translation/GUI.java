package translation;

import javax.swing.*;
import java.awt.event.*;

public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            JPanel countryPanel = new JPanel();
            countryPanel.add(new JLabel("Country:"));

            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));

            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);

            // Use converters and JSONTranslator
            Translator translator = new JSONTranslator();
            CountryCodeConverter countryConverter = new CountryCodeConverter();
            LanguageCodeConverter languageConverter = new LanguageCodeConverter();

            // ComboBox for countries
            JComboBox<String> countryComboBox = new JComboBox<>();
            for (String code : translator.getCountryCodes()) {
                String name = countryConverter.fromCountryCode(code);
                countryComboBox.addItem(name != null ? name : code);
            }
            countryPanel.add(countryComboBox);

            // JList for languages
            DefaultListModel<String> langListModel = new DefaultListModel<>();
            for (String code : languageConverter.languageCodeToLanguage.keySet()) {
                String name = languageConverter.fromLanguageCode(code);
                langListModel.addElement(name != null ? name : code);
            }
            JList<String> languageList = new JList<>(langListModel);
            languageList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            JScrollPane scrollPane = new JScrollPane(languageList);
            scrollPane.setPreferredSize(new java.awt.Dimension(150, 100));
            languagePanel.add(scrollPane);

            // ActionListener for translation
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedCountryName = (String) countryComboBox.getSelectedItem();
                    String countryCode = countryConverter.fromCountry(selectedCountryName);

                    String selectedLangName = languageList.getSelectedValue();
                    String languageCode = languageConverter.fromLanguage(selectedLangName);

                    String result = translator.translate(countryCode, languageCode);
                    if (result == null || result.contains("not implemented")) {
                        result = "no tra`nslation found!";
                    }
                    resultLabel.setText(result);
                }
            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
