Brewer.Venda = (function() {
	
	function Venda(tabelaItens) {
		this.tabelaItens = tabelaItens;
		this.valorTotalBox = $('.js-valor-total-box');
		this.valorFreteInput = $('#valorFrete');
		this.valorDescontoInput = $('#valorDesconto');
		this.valorTotalBoxContainer = $('.js-valor-total-box-container');
		
		this.valorTotalItens = this.tabelaItens.valorTotal();
		this.valorFrete = this.valorFreteInput.data('valor');
		this.valorDesconto = this.valorDescontoInput.data('valor');
	}
	
	Venda.prototype.iniciar = function() {
		this.tabelaItens.on('tabela-itens-atualizada', onTabelaItensAtualizada.bind(this));
		this.valorFreteInput.on('keyup', onValorFreteAlterado.bind(this));
		this.valorDescontoInput.on('keyup', onValorDescontoAlterado.bind(this));
		
		this.tabelaItens.on('tabela-itens-atualizada', onValoresAlterados.bind(this));
		this.valorFreteInput.on('keyup', onValoresAlterados.bind(this));
		this.valorDescontoInput.on('keyup', onValoresAlterados.bind(this));
		
		onValoresAlterados.call(this);
	}
	
	function onTabelaItensAtualizada(evento, valorTotalItens) {
		this.valorTotalItens = parseFloat(valorTotalItens);
	}
	
	function onValorFreteAlterado(evento) {
		this.valorFrete = Brewer.recuperarValor($(evento.target).val());
	}
	
	function onValorDescontoAlterado(evento) {
		this.valorDesconto = Brewer.recuperarValor($(evento.target).val());
	}
	
	function onValoresAlterados() {
		var valorTotal = (this.valorTotalItens == null ? 0 : parseFloat(this.valorTotalItens)) + (this.valorFrete == null ? 0 : parseFloat(this.valorFrete)) - (this.valorDesconto == null ? 0 : parseFloat(this.valorDesconto));
		this.valorTotalBox.html(Brewer.formatarMoeda(valorTotal));
		
		this.valorTotalBoxContainer.toggleClass('negativo', valorTotal < 0);
		
		console.log('valorTotalItens', numeral(this.valorTotalItens).value());
		console.log('valorFrete', numeral(this.valorFrete).value());
		console.log('valorDesconto', numeral(this.valorDesconto).value());
	}
	
	return Venda;
	
}());

$(function() {
	
	var autocomplete = new Brewer.Autocomplete();
	autocomplete.iniciar();
	
	var tabelaItens = new Brewer.TabelaItens(autocomplete);
	tabelaItens.iniciar();
	
	var venda = new Brewer.Venda(tabelaItens);
	venda.iniciar();
	
});