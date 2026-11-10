public class Passageiro {

    private String nome;
    private int idade;
    private String cpf;
    private String email;

    public Passageiro(String nome, int idade, String cpf, String email){
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
        this.email = email;

    }

    public  String getNome(){
        return nome;
    }
    public int getidade(){
        return idade;
    }
    public String getcpf(){
        return cpf;
    }
    public String getemail(){
        return email;
    }

    public void exibir() {
        System.out.println("  Nome: " + nome);
        System.out.println("  Idade: " + idade);
        System.out.println("  CPF: " + cpf);
        System.out.println("  Email: " + email);
    }

    // foi criado atributos privados dos passagereiro
    //juntamente com o metodo que pega os dados do passageiro

    



}