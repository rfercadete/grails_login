import grails.plugin.bcrypt.BCrypt

class Usuario {
    Date dateCreated
    Date lastUpdated

    String senha
    String confirmacaoDeSenha
    String nome
    String email
    Boolean admin = false
    String esqueciMinhaSenha

    static transients = ["confirmacaoDeSenha"]

    static hasMany = [papeis: Papel]

    static constraints = {
        nome nullable: false, blank: false
        email nullable: false, blank: false, email: true, unique: true
        senha nullable: false, blank: false, password: true, bindable: false
        senha validator: { val, obj ->
            if (!obj.id && val != obj.confirmacaoDeSenha) return ['senhaNaoConfere']
        }
        confirmacaoDeSenha bindable: false
        admin bindable: false
        esqueciMinhaSenha nullable: true
    }

    static mapping = {
        papeis lazy: false
    }

    def beforeInsert() {
        encripteSenha()
    }

    String toString() {
        email
    }

    boolean podeAcessar(String funcionalidade) {
        funcionalidade && (admin || papeisPodemAcessar(funcionalidade))
    }

    private boolean papeisPodemAcessar(String funcionalidade) {
        papeis?.any { it.podeAcessar(funcionalidade) }
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Usuario usuario = (Usuario) o

        if (email != usuario.email) return false

        return true
    }

    int hashCode() {
        return email.hashCode()
    }

    boolean criarSenhaComEsqueciMinhaSenha(String novaSenha, String confirmacaoNovaSenha) {
        if (criarNovaSenha(novaSenha, confirmacaoNovaSenha)) {
            esqueciMinhaSenha = null
            true
        } else {
            false
        }
    }

    boolean criarNovaSenha(String novaSenha, String confirmacaoNovaSenha) {
        if (novaSenha == confirmacaoNovaSenha) {
            senha = novaSenha
            encripteSenha()
        } else {
            false
        }
    }

    boolean alterarSenha(String senhaAtual, String novaSenha, String confirmacaoNovaSenha) {
        if (BCrypt.checkpw(senhaAtual, senha)) {
            criarNovaSenha(novaSenha, confirmacaoNovaSenha)
        } else {
            false
        }
    }

    private encripteSenha() {
        confirmacaoDeSenha = senha = BCrypt.hashpw(senha, BCrypt.gensalt())
    }

    void gerarEsqueciMinhaSenha() {
        esqueciMinhaSenha = UUID.randomUUID().toString()
    }
}
