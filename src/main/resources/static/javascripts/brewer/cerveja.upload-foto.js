var Brewer = Brewer || {};

Brewer.UploadFoto = (function() {
	
	function UploadFoto() {
		this.inputNomeFoto = $('input[name=foto]');
		this.inputContentType = $('input[name=contentType]');
        
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
			var responseJSON = { nome: this.inputNomeFoto.val(), contentType: this.inputContentType.val() };
			onUploadCompleto.call(this, { responseJSON });
		}
	}
	
	function onUploadCompleto(resposta) {
		this.inputNomeFoto.val(resposta.responseJSON.nome);
        this.inputContentType.val(resposta.responseJSON.contentType);
        
        this.uploadDrop.addClass('hidden');
        var htmlFotoCerveja = this.template({nomeFoto: resposta.responseJSON.nome});
        this.containerFotoCerveja.append(htmlFotoCerveja);
        
        $('.js-remove-foto').on('click', onRemoverFoto.bind(this));
	}
	
	function onRemoverFoto() {
		$('.js-foto-cerveja').remove();
	    	this.uploadDrop.removeClass('hidden');
	    	this.inputNomeFoto.val('');
	    	this.inputContentType.val('');
	}
	
	return UploadFoto;
	
})();

$(function() {
	var uploadFoto = new Brewer.UploadFoto();
	uploadFoto.iniciar();
});