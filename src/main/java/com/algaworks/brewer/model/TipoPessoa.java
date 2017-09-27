package com.algaworks.brewer.model;

import com.algaworks.brewer.model.validation.group.CnpjGroup;
import com.algaworks.brewer.model.validation.group.CpfGroup;

public enum TipoPessoa {

    FISICA("Física", "CPF", "000.000.000-00", CpfGroup.class) {
        @Override
        public String formatar(final String cpfOuCnpj) {
            return cpfOuCnpj.replaceAll("(\\d{3})(\\d{3})(\\d{3})", "$1.$2.$3-");
        }
    },
    JURIDICA("Jurídica", "CNPJ", "00.000.000/0000-00", CnpjGroup.class) {
        @Override
        public String formatar(final String cpfOuCnpj) {
            return cpfOuCnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})", "$1.$2.$3/$4-");
        }
    };

    private final String descricao;
    private final String documento;
    private final String mascara;
    private final Class<?> grupo;

    private TipoPessoa(final String descricao, final String documento, final String mascara, final Class<?> grupo) {
        this.descricao = descricao;
        this.documento = documento;
        this.mascara = mascara;
        this.grupo = grupo;
    }

    public abstract String formatar(final String cpfOuCnpj);

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * @return the documento
     */
    public String getDocumento() {
        return this.documento;
    }

    /**
     * @return the mascara
     */
    public String getMascara() {
        return this.mascara;
    }

    /**
     * @return the grupo
     */
    public Class<?> getGrupo() {
        return this.grupo;
    }

    public static String removerFormatacao(final String cpfOuCnpj) {
        return cpfOuCnpj.replaceAll("\\.|-|/", "");
    }

}
