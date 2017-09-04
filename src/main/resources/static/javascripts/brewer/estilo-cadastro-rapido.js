var Brewer = Brewer || {};

Brewer.EstiloCadastroRapido = (function() {

	function EstiloCadastroRapido() {
		this.modal = $("#modalCadastroRapidoEstilo");
		this.botaoSalvar = this.modal.find("#btn-salvar-modal-cadastro-rapido-estilo");
		this.form = this.modal.find("form");
		this.containerMsgErro = $("#msg-erro-cadastro-rapido-estilo");
		this.url = this.form.attr("action");
		this.inputNomeEstilo = $("#nome-estilo");
	}

	EstiloCadastroRapido.prototype.iniciar = function() {
		this.form.on("submit", function(e) {
			e.preventDefault();
		});
		this.modal.on("shown.bs.modal", onModalShow.bind(this));
		this.modal.on("hide.bs.modal", onModalClose.bind(this));
		this.botaoSalvar.on("click", onBotaoSalvarClick.bind(this));
	}
	
	function onModalShow() {
		this.inputNomeEstilo.focus();
	}
	
	function onModalClose() {
		this.containerMsgErro.addClass("hidden");
		this.containerMsgErro.html("");
		this.form.find(".form-group").removeClass("has-error");
		this.inputNomeEstilo.val("");
	}
	
	function onBotaoSalvarClick() {
		var nomeEstilo = this.inputNomeEstilo.val().trim();
		$.ajax({
			url : this.url,
			method : "POST",
			contentType : "application/json",
			data : JSON.stringify({
				nome : nomeEstilo
			}),
			error: onErroSalvandoEstilo.bind(this),
			success: onEstiloSalvo.bind(this)
		});
	}
	
	function onErroSalvandoEstilo(obj) {
		var mensagemErro = obj.responseText;
		this.containerMsgErro.removeClass("hidden");
		this.containerMsgErro.html("<span>" + mensagemErro + "</span>");
		this.form.find(".form-group").addClass("has-error");
	}
	
	function onEstiloSalvo(estilo) {
		var comboEstilo = $("#estilo");
		comboEstilo.append("<option value=" + estilo.codigo + ">" + estilo.nome + "</option>");
		comboEstilo.val(estilo.codigo);
		this.modal.modal("hide");
	}
	
	return EstiloCadastroRapido;

}());

$(function() {
	var estiloCadastroRapido = new Brewer.EstiloCadastroRapido();
	estiloCadastroRapido.iniciar();
});