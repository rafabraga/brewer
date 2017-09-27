package com.algaworks.brewer.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class UsuarioGrupoId implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "codigo_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "codigo_grupo")
    private Grupo grupo;

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(final Usuario usuario) {
        this.usuario = usuario;
    }

    public Grupo getGrupo() {
        return this.grupo;
    }

    public void setGrupo(final Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.grupo == null) ? 0 : this.grupo.hashCode());
        result = (prime * result) + ((this.usuario == null) ? 0 : this.usuario.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final UsuarioGrupoId other = (UsuarioGrupoId) obj;
        if (this.grupo == null) {
            if (other.grupo != null) {
                return false;
            }
        } else if (!this.grupo.equals(other.grupo)) {
            return false;
        }
        if (this.usuario == null) {
            if (other.usuario != null) {
                return false;
            }
        } else if (!this.usuario.equals(other.usuario)) {
            return false;
        }
        return true;
    }

}