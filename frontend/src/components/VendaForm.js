import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {useNavigate, useParams } from 'react-router-dom';
import Menu from "./Menu/Menu";
import './VendaForm.css';

const VendaForm = () => {


    const [venda, setVenda] = useState({
        vendedor: '',
        itens: [
            { produtoCodigo: '', quantidade: 1, precoUnitario: 0.0 }
        ]
    });

    const [produtos, setProdutos] = useState([]);
    const [mensagem, setMensagem] = useState('');
    const [errors, setErrors] = useState({});

    const navigate = useNavigate();
    const { id } = useParams();

    useEffect(() => {
        fetchProdutos();
        if (id) {
            fetchVenda(id);
        }
    }, [id]);

    const fetchProdutos = async () => {
        try {
            const response = await axios.get('/api/produtos/listar');
            setProdutos(response.data);
        } catch (error) {
            console.error('Erro ao buscar produtos:', error);
        }
    };

    const fetchVenda = async (id) => {
        try {
            const response = await axios.get(`/api/vendas/buscar/${id}`);
            setVenda(response.data);
        } catch (error) {
            console.error('Erro ao buscar venda:', error);
            setMensagem('Erro ao buscar venda.');
        }
    };

    const handleInputChange = (index, event) => {
        const { name, value } = event.target;
        const novosItens = [...venda.itens];
        novosItens[index][name] = name === 'quantidade' ? Number(value) : value;

        if (name === 'produtoCodigo') {
            const produto = produtos.find(p => p.codigo === value);
            if (produto) {
                novosItens[index]['precoUnitario'] = produto.preco;
            } else {
                novosItens[index]['precoUnitario'] = 0.0;
            }
        }

        setVenda({ ...venda, itens: novosItens });
    };

    const addItem = () => {
        setVenda({
            ...venda,
            itens: [...venda.itens, { produtoCodigo: '', quantidade: 1, precoUnitario: 0.0 }]
        });
    };

    const removeItem = (index) => {
        const novosItens = [...venda.itens];
        novosItens.splice(index, 1);
        setVenda({ ...venda, itens: novosItens });
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            if (id) {
                await axios.put(`/api/vendas/atualizar/${id}`, venda);
                setMensagem('Venda atualizada com sucesso!');
            } else {
                await axios.post('/api/vendas/salvar', venda);
                setMensagem('Venda salva com sucesso!');
            }
            setErrors({});
            navigate('/vendas');
        } catch (error) {
            console.error('Erro ao salvar a venda:', error.response?.data || error.message);
            if (error.response && error.response.status === 400) {
                setErrors(error.response.data);
                setMensagem('Erro ao salvar a venda.');
            } else if (error.response && error.response.status === 409) {
                setMensagem('Erro ao salvar a venda.');
            } else {
                setMensagem('Erro ao salvar a venda.');
            }
        }
    };

    return (
        <div className='containervendas'>
            <Menu/>
            {/* <h2>{id ? 'Editar Venda' : 'Nova Venda'}</h2> */}
            <form onSubmit={handleSubmit}>
                <div>
                    <h2>{id ? 'Editar Venda' : 'Nova Venda'}</h2>
                    <label>Vendedor:</label>
                    <input
                        type="text"
                        name="vendedor"
                        value={venda.vendedor}
                        onChange={(e) => setVenda({...venda, vendedor: e.target.value})}
                        required
                    />
                    {errors.vendedor && <span style={{color: 'red'}}>{errors.vendedor}</span>}
                </div>
                <h3>Itens</h3>
                {venda.itens.map((item, index) => (
                    <div key={index} style={{border: '1px solid #ccc', padding: '10px', marginBottom: '10px'}}>
                        <div>
                            <label>Produto:</label>
                            <select
                                name="produtoCodigo"
                                value={item.produtoCodigo}
                                onChange={(e) => handleInputChange(index, e)}
                                required
                            >
                                <option value="">Selecione</option>
                                {produtos.map(produto => (
                                    <option key={produto.id} value={produto.codigo}>
                                        {produto.nome} - {produto.codigo}
                                    </option>
                                ))}
                            </select>
                            {errors[`itens[${index}].produtoCodigo`] &&
                                <span style={{color: 'red'}}>{errors[`itens[${index}].produtoCodigo`]}</span>}
                        </div>
                        <div>
                            <label>Quantidade:</label>
                            <input
                                type="number"
                                name="quantidade"
                                value={item.quantidade}
                                onChange={(e) => handleInputChange(index, e)}
                                required
                                min="1"
                                step="1"
                            />
                            {errors[`itens[${index}].quantidade`] &&
                                <span style={{color: 'red'}}>{errors[`itens[${index}].quantidade`]}</span>}
                        </div>
                        <div>
                            <label>Preço Unitário:</label>
                            <input
                                type="number"
                                name="precoUnitario"
                                value={item.precoUnitario}
                                readOnly
                                step="0.01"
                            />
                        </div>
                        <button type="button" onClick={() => removeItem(index)}>Remover Item</button>
                    </div>
                ))}
                <button type="button" onClick={addItem}>Adicionar Item</button>
                <button type="submit">Salvar Venda</button>
            </form>
            {mensagem && <p>{mensagem}</p>}
        </div>
    );
};

export default VendaForm;
