var Brewer = Brewer || {};

Brewer.UploadFoto = (function() {
	
	function UploadFoto() {
		this.inputNomeFoto = $('input[name=foto]');
		this.inputContentType = $('input[name=contentType]');
		this.novaFoto = $('input[name=novaFoto]');
		this.inputUrlFoto = $('input[name=urlFoto]');
        
		this.htmlFotoCervejaTemplate = $('#foto-cerveja').html();
		this.template = Handlebars.compile(this.htmlFotoCervejaTemplate);
        
		this.containerFotoCerveja = $('.js-container-foto-cerveja');
        
		this.uploadDrop = $('#upload-drop');
	}
	
	UploadFoto.prototype.iniciar = function() {
		var settings = {
            filelimit: 1,
            allow: '*.(jpg|jpeg|png)',
            url: this.containerFotoCerveja.data('url-fotos'),
            complete: onUploadCompleto.bind(this)
		}
		
		UIkit.upload($('#upload-drop'), settings);
		
		if (this.inputNomeFoto.val()) {
			var responseJSON = { nome: this.inputNomeFoto.val(), contentType: this.inputContentType.val(), url: this.inputUrlFoto.val() };
			renderizarFoto.call(this, { responseJSON });
		}
	}
	
	function onUploadCompleto(resposta) {
		this.novaFoto.val('true');
		this.inputUrlFoto.val(resposta.url);
		renderizarFoto.call(this, resposta);
	}
	
	function renderizarFoto(resposta) {
		this.inputNomeFoto.val(resposta.responseJSON.nome);
        this.inputContentType.val(resposta.responseJSON.contentType);
        
        this.uploadDrop.addClass('hidden');
        
        var htmlFotoCerveja = this.template({url: resposta.url});
        this.containerFotoCerveja.append(htmlFotoCerveja);
        
        $('.js-remove-foto').on('click', onRemoverFoto.bind(this));
	}
	
	function onRemoverFoto() {
		$('.js-foto-cerveja').remove();
	    	this.uploadDrop.removeClass('hidden');
	    	this.inputNomeFoto.val('');
	    	this.inputContentType.val('');
	    	this.novaFoto.val('false');
	}
	
	return UploadFoto;
	
})();

$(function() {
	var uploadFoto = new Brewer.UploadFoto();
	uploadFoto.iniciar();
});