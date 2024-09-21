import React, { useState } from 'react';
import axios from 'axios';

const FormProduto = () => {
    const [produto, setProduto] = useState({
        nome: '',
        descricao: '',
        preco: 0,
        quantidade: 0,
        codigo: ''
    });

    const [mensagem, setMensagem] = useState('');

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setProduto({ ...produto, [name]: value });
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            // Fazendo a requisição POST para salvar o produto
            const response = await axios.post('localhost:8080/api/produtos/salvar', produto);
            setMensagem('Produto salvo com sucesso!');
            console.log('Produto salvo:', response.data);
        } catch (error) {
            console.error('Erro ao salvar o produto:', error.response?.data || error.message);
            setMensagem('Erro ao salvar o produto.');
        }
    };

    return (
        <div>
            <h2>Salvar Produto</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Nome:</label>
                    <input
                        type="text"
                        name="nome"
                        value={produto.nome}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <div>
                    <label>Descrição:</label>
                    <input
                        type="text"
                        name="descricao"
                        value={produto.descricao}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <div>
                    <label>Preço:</label>
                    <input
                        type="number"
                        name="preco"
                        value={produto.preco}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <div>
                    <label>Quantidade:</label>
                    <input
                        type="number"
                        name="quantidade"
                        value={produto.quantidade}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <div>
                    <label>Código:</label>
                    <input
                        type="text"
                        name="codigo"
                        value={produto.codigo}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <button type="submit">Salvar Produto</button>
            </form>
            {mensagem && <p>{mensagem}</p>}
        </div>
    );
};

export default FormProduto;
