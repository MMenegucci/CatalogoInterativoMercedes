import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.lang.reflect.Field;

public class Program {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        System.out.println("|----------------------------------------------|");
        System.out.println("           Seja Bem-Vindo à Mercedes!       ");
        System.out.println("|----------------------------------------------|");
        System.out.println();
        System.out.println("Primeiramente, gostariamos de saber qual modelo lhe chama mais a atenção?");
        System.out.println();
        System.out.println("Temos disponíveis SUVs, Sedans, Hatchback!");
        System.out.println();
        System.out.println("Digite uma das seguintes opções sugeridas: SUV, SEDAN e Hatchback:");

        String[] models = {};
        String category = scanner.nextLine().trim();

        try {
            if (category.equalsIgnoreCase("SUV")) {
                System.out.println("Essas são nossas opções de SUVs: EQA, EQB, GLA, GLB, GLC, GLE e GLS");
                System.out.println();
                models = new String[]{"EQA", "EQB", "GLA", "GLB", "GLC", "GLE", "GLS"};
            } else if (category.equalsIgnoreCase("SEDAN")) {
                System.out.println("Essas são nossas opções de Sedans: ClassCSedan, ClassESedan e EQESedan");
                System.out.println();
                models = new String[]{"ClassCSedan", "ClassESedan", "EQESedan"};
            } else if (category.equalsIgnoreCase("Hatchback")) {
                System.out.println("Hatchback temos somente uma unidade sendo o ClassAHatchback.");
                System.out.println();
                models = new String[]{"ClassAHatchback"};
            } else {
                System.out.println("Categoria não encontrada. Por favor, escolha entre SUV, Sedan ou Hatchback.");
                return;
            }

            System.out.println("Qual modelo você deseja ver?");
            String model = scanner.nextLine().trim();

            String fullClassName = "Cars." + category + "." + model;

            Map<String, String> descriptions = new HashMap<>();
            descriptions.put("Preço", "Preço");
            descriptions.put("Potencia", "Potência em Cavalos");

            try {
                Class<?> carClass = Class.forName(fullClassName);
                Object carInstance = carClass.getDeclaredConstructor().newInstance();

                System.out.println("\nInformações do modelo: " + model);
                for (Field field : carClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    Object fieldValue = field.get(carInstance);

                    String description = descriptions.get(fieldName);
                    if (description != null) {
                        if (fieldName.equalsIgnoreCase("Preço")) {
                            DecimalFormat df = new DecimalFormat("$#,###.000");
                            System.out.println(description + ": " + df.format(fieldValue));
                        } else if (fieldName.equalsIgnoreCase("Potencia")) {
                            System.out.println(description + ": " + fieldValue + " cv");
                        } else {
                            System.out.println(description + ": " + fieldValue);
                        }
                    } else {
                        System.out.println(fieldName + ": " + fieldValue);
                    }
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Erro: Modelo não encontrado.");
            } catch (Exception e) {
                System.out.println("Ocorreu um erro: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro " + e.getMessage());
        }
        scanner.close();
     }
  }