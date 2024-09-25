import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';
import './FormProduto.css'
import Menu from "./Menu/Menu";

const FormProduto = () => {
    const [produto, setProduto] = useState({
        nome: '',
        descricao: '',
        preco: 0.0,
        quantidade: 0,
        codigo: ''
    });

    const [mensagem, setMensagem] = useState('');
    const [errors, setErrors] = useState({});

    const navigate = useNavigate();
    const { id } = useParams();

    useEffect(() => {
        if (id) {
            fetchProduto(id);
        }
    }, [id]);

    const fetchProduto = async (id) => {
        try {
            const response = await axios.get(`/api/produtos/buscar/${id}`);
            setProduto(response.data);
        } catch (error) {
            console.error('Erro ao buscar produto:', error);
            setMensagem('Erro ao buscar produto.');
        }
    };

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setProduto({ 
            ...produto, 
            [name]: (name === 'preco' || name === 'quantidade') ? Number(value) : value 
        });
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            if (id) {
                await axios.put(`/api/produtos/atualizar/${id}`, produto);
                setMensagem('Produto atualizado com sucesso!');
            } else {
                await axios.post('/api/produtos/salvar', produto);
                setMensagem('Produto salvo com sucesso!');
            }
            setErrors({});
            navigate('/produtos');
        } catch (error) {
            console.error('Erro ao salvar o produto:', error.response?.data || error.message);
            if (error.response && error.response.status === 400) {
                setErrors(error.response.data);
                setMensagem('Erro ao salvar o produto.');
            } else if (error.response && error.response.status === 409) {
                setErrors({ codigo: "Código já existente. Por favor, escolha outro código." });
                setMensagem('Erro ao salvar o produto.');
            } else {
                setMensagem('Erro ao salvar o produto.');
            }
        }
    };

    return (
        <div className= 'containernovoproduto'>
            <Menu/>
            <div className='caixa'>
                <h2>{id ? 'Editar Produto' : 'Novo Produto'}</h2>
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
                        {errors.nome && <span style={{color: 'red'}}>{errors.nome}</span>}
                    </div>
                    <div>
                        <label>Descrição:</label>
                        <input
                            type="text"
                            name="descricao"
                            value={produto.descricao}
                            onChange={handleInputChange}
                        />
                        {errors.descricao && <span style={{color: 'red'}}>{errors.descricao}</span>}
                    </div>
                    <div>
                        <label>Preço:</label>
                        <input
                            type="number"
                            name="preco"
                            value={produto.preco}
                            onChange={handleInputChange}
                            required
                            min="0.01"
                            step="0.01"
                        />
                        {errors.preco && <span style={{color: 'red'}}>{errors.preco}</span>}
                    </div>
                    <div>
                        <label>Quantidade:</label>
                        <input
                            type="number"
                            name="quantidade"
                            value={produto.quantidade}
                            onChange={handleInputChange}
                            required
                            min="0"
                            step="1"
                        />
                        {errors.quantidade && <span style={{color: 'red'}}>{errors.quantidade}</span>}
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
                        {errors.codigo && <span style={{color: 'red'}}>{errors.codigo}</span>}
                    </div>
                    <button type="submit">Salvar Produto</button>
                </form>
            </div>
            {mensagem && <p className='alerta'>{mensagem}</p>}
        </div>
    );
};

export default FormProduto;
