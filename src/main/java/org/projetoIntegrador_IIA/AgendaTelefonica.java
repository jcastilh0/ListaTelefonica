package org.projetoIntegrador_IIA;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Classe responsável pelo gerenciamento de contatos em um banco de dados MySQL.
 */
public class AgendaTelefonica {

    private final String driver = "com.mysql.cj.jdbc.Driver";
    private final String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
    private final String user = "root";
    private final String password = "123456789";

    /**
     * Realiza a conexão com o banco de dados.
     *
     * @return Objeto Connection conectado ao banco ou null em caso de erro.
     */
    private Connection conectar() {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            return null;
        }
    }

    /**
     * Adiciona um novo contato à base de dados.
     *
     * @param contato Objeto Contato com nome, telefone e e-mail preenchidos.
     */
    public void adicionarContato(Contato contato) {

        if (contato.getNome() == null || contato.getNome().trim().isEmpty() ||
                contato.getTelefone() == null || contato.getTelefone().trim().isEmpty()) {
            System.out.println("Erro: nome e telefone são obrigatórios para adicionar um contato.");
            return;
        }

        String sql = "INSERT INTO contatos (nome, fone, email) VALUES (?, ?, ?)";

        try (Connection con = conectar();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, contato.getNome());
            pst.setString(2, contato.getTelefone());
            pst.setString(3, contato.getEmail());
            pst.executeUpdate();

        } catch (Exception e) {
            System.err.println("Erro ao adicionar contato: " + e.getMessage());
        }
    }

    /**
     * Busca e preenche os dados do contato com base no ID ou nome.
     *
     * @param contato Objeto Contato com ID ou nome preenchido.
     */
    public void buscarContato(Contato contato) {
        String sql = "SELECT * FROM contatos WHERE idcon = ? OR nome = ?";

        try (Connection con = conectar();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, contato.getIdcon());
            pst.setString(2, contato.getNome());
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    contato.setIdcon(rs.getString("idcon"));
                    contato.setNome(rs.getString("nome"));
                    contato.setTelefone(rs.getString("fone"));
                    contato.setEmail(rs.getString("email"));
                } else {
                    System.out.println("Contato não encontrado.");
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao buscar contato: " + e.getMessage());
        }
    }

    /**
     * Lista todos os contatos cadastrados, ordenados por nome.
     *
     * @return Lista de objetos Contato.
     */
    public ArrayList<Contato> listarContatos() {
        ArrayList<Contato> contatos = new ArrayList<>();
        String read = "SELECT * FROM contatos ORDER BY nome";
        try (Connection con = conectar();
             PreparedStatement pst = con.prepareStatement(read);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String idcon = rs.getString("idcon");
                String nome = rs.getString("nome");
                String telefone = rs.getString("fone");
                String email = rs.getString("email");

                contatos.add(new Contato(idcon, nome, telefone, email));
            }

            return contatos;
        } catch (Exception e) {
            System.out.println("Erro ao listar contatos: " + e.getMessage());
            return null;
        }
    }


    /**
     * Remove um contato da base de dados com base no ID ou nome.
     *
     * @param contato Objeto Contato com ID ou nome preenchido.
     */
    public void removerContato(Contato contato) {
        String sql = "DELETE FROM contatos WHERE idcon = ? OR nome = ?";

        try (Connection con = conectar();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, contato.getIdcon());
            pst.setString(2, contato.getNome());
            int linhasAfetadas = pst.executeUpdate();

            if (linhasAfetadas == 0) {
                System.out.println("Nenhum contato foi removido.");
            }

        } catch (Exception e) {
            System.err.println("Erro ao remover contato: " + e.getMessage());
        }
    }
}
