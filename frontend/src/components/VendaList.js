import React, { useEffect, useState } from 'react';
import axios from 'axios';
import {useNavigate} from "react-router-dom";
import Menu from "./Menu";

const VendaList = () => {

    const [vendas, setVendas] = useState([]);
    const [vendedor, setVendedor] = useState(''); // Estado para nome do vendedor
    const [dataVenda, setDataVenda] = useState(''); // Estado para data da venda

    useEffect(() => {
        fetchVendas();
    }, []);

    const fetchVendas = async () => {
        try {
            const response = await axios.get('/api/vendas/listar');
            setVendas(response.data);
        } catch (error) {
            console.error('Erro ao buscar vendas:', error);
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm('Tem certeza que deseja excluir esta venda?')) {
            try {
                await axios.delete(`/api/vendas/excluir/${id}`);
                setVendas(vendas.filter(venda => venda.id !== id));
            } catch (error) {
                console.error('Erro ao excluir venda:', error);
                alert('Erro ao excluir venda.');
            }
        }
    };

    const handleSearch = async (e) => {
        e.preventDefault();
        try {
            const params = {};

            if (vendedor) params.vendedor = vendedor;
            if (dataVenda) params.data = dataVenda;

            const response = await axios.get('/api/vendas/listar', { params });
            setVendas(response.data);
        } catch (error) {
            console.error('Erro ao buscar vendas:', error);
        }
    };

    // Função para limpar os campos de pesquisa
    const handleClear = () => {
        setVendedor('');  // Limpa o campo vendedor
        setDataVenda('');  // Limpa o campo data
        fetchVendas();     // Recarrega todas as vendas
    };

    return (
        <div>
            <Menu/>
            <h2>Lista de Vendas</h2>
            <form onSubmit={handleSearch}>
                <input
                    type="text"
                    placeholder="Buscar por vendedor"
                    value={vendedor}
                    onChange={(e) => setVendedor(e.target.value)} // Atualiza o estado do vendedor
                />
                <input
                    type="date"
                    value={dataVenda}
                    onChange={(e) => setDataVenda(e.target.value)} // Atualiza o estado da data
                />
                <button type="submit">Buscar</button>
                <button type="button" onClick={handleClear}>Limpar</button>
                {/* Botão para limpar */}
            </form>
            <table border="1">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Data</th>
                    <th>Vendedor</th>
                    <th>Produtos Comprados</th>
                    <th>Valor Total</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                {vendas.map(venda => (
                    <tr key={venda.id}>
                        <td>{venda.id}</td>
                        <td>{new Date(venda.data).toLocaleString()}</td>
                        <td>{venda.vendedor}</td>
                        <td>
                            <ul>
                                {venda.itens.map(item => (
                                    <li key={item.id}>
                                        {item.produto.nome} - Quantidade: {item.quantidade} - Valor Total:
                                        R${(item.quantidade * item.precoUnitario).toFixed(2)}
                                    </li>
                                ))}
                            </ul>
                        </td>
                        <td>{venda.valorTotal.toFixed(2)}</td>
                        <td>
                            <button onClick={() => handleDelete(venda.id)}>Excluir</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default VendaList;
