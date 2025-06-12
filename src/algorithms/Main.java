package algorithms;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    // Método para validar se o arquivo está no formato correto
    private static Boolean validateFileContentFormat(String[] fileContent) { 
        // Se o arquivo possuir mais que 3 linhas, o arquivo é inválido
        if(fileContent.length > 3) { 
            return false;
        }
        
        if(!Pattern.matches("^([A-Za-z]+)( [A-Za-z]+)*$", fileContent[0]) || !Pattern.matches("^(\\d+)( \\d+)*$", fileContent[1]) || !Pattern.matches("^(\\d+)( \\d+)*$", fileContent[2])) { 
            return false;
        }
        
        return true;
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        String fileName;
        String[] fileContent;
        List<String[]> fileContentMatrix = new ArrayList<>();
        Scanner s = ScannerSingleton.getInstance();
        
        System.out.println("Seja bem-vindo ao programa de escalonamento de processos!");
        System.out.print("Digite o nome do arquivo que gostaria de abrir: ");
        fileName = s.next();
        
        // Lê o arquivo a partir do Path
        while(true) { 
            try { 
                fileContent = Files.lines(Paths.get(fileName)).map(String::valueOf).toArray(String[]::new);
                if(validateFileContentFormat(fileContent)) { 
                    for(int a = 0; a < 3; a++) { 
                        fileContentMatrix.add(fileContent[a].split(" "));
                    }
                    if(fileContentMatrix.get(0).length != fileContentMatrix.get(1).length || fileContentMatrix.get(1).length != fileContentMatrix.get(2).length) { 
                        System.out.println("A quantidade de IDs, tempo de processo ou de chegada é incompatível.");
                    }
                    else {
                        break;
                    }
                }
                else {
                    System.out.println("O arquivo não possui um formato aceito para entrada de processos.");
                    throw new IOException();
                }
            }
            catch(IOException e) { 
                String filePath = System.getProperty("user.dir");
                File[] filesList = new File(filePath).listFiles();
                System.out.printf("Arquivos disponíveis: ");
                for (File file : filesList) {
                    System.out.printf(" %s ", file.getName());
                }
                
                System.out.printf("\nDigite novamente: ");
                fileName = s.next();
            }
        }
        
        System.out.println("Tempo de chegada do processo: ");
        System.out.println("\nOs métodos disponíveis para escalonamento de processos são: ");
        System.out.println("1 - Round Robin");
        System.out.println("2 - Shortest Job First");
        System.out.printf("\nDigite a sua opção: ");
         
        switch(s.nextInt()) { 
            // Round robin
            case 1 -> {
                RoundRobin roundRobin = new RoundRobin();

                for(int a = 0; a < fileContentMatrix.get(0).length; a++) { 
                    roundRobin.addProcessToAllProcessesList(new Process(fileContentMatrix.get(0)[a], fileContentMatrix.get(1)[a], fileContentMatrix.get(2)[a]));
                }
                RoundRobin.showListContent(roundRobin.getAllProcessesList());
                // Roda o Round Robin, as informações da execução são mostradas ao fim do código
                
                // roundRobin.run(false, true): roda o RoundRobin sem printar diálogos de processos em execução e com fast mode.
                 roundRobin.run(false, true);
                
                // roundRobin.run(true, false): roda o RoundRobin printando diálogos e simulando a execução com quantum e passo de 1 segundo
//                roundRobin.run(true, false);
            }
            // SJF
            case 2 -> {
                SJF sjf = new SJF(true, true);
                List<Process> allProcessesList = sjf.getAllProcessesList();

                // Coleta todos os processos no arquivo e joga pra lista de todos os processos
                for(int a = 0; a < fileContentMatrix.get(0).length; a++) { 
                    allProcessesList.add(new Process(fileContentMatrix.get(0)[a], fileContentMatrix.get(1)[a], fileContentMatrix.get(2)[a]));
                }

                sjf.setAllProcessesList(allProcessesList);

                sjf.run();
            }
            default -> System.out.println("Opção inválida!");
        }
        
    }
    
}
