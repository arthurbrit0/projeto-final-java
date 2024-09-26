import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Menu from "./Menu/Menu";
import './VendaList.css';

const VendaList = () => {

    const [vendas, setVendas] = useState([]);
    const [vendedor, setVendedor] = useState('');
    const [dataVenda, setDataVenda] = useState('');

    useEffect(() => {
        fetchVendas();
    }, []);

    const fetchVendas = async () => {
        try {
            let url = 'http://localhost:8080/api/vendas/listar';
            const params = [];

            if (vendedor) {
                params.push(`vendedor=${encodeURIComponent(vendedor)}`);
            }
            if (dataVenda) {
                params.push(`data=${dataVenda}`);
            }
            if (params.length > 0) {
                url += `?${params.join('&')}`;
            }

            const response = await axios.get(url);
            console.log('Dados recebidos:', response.data);
            setVendas(response.data);
        } catch (error) {
            console.error('Erro ao buscar vendas:', error);
        }
    };

    const handleSearch = (e) => {
        e.preventDefault();
        fetchVendas();
    };

    const handleDelete = async (id) => {
        if (window.confirm('Tem certeza que deseja excluir esta venda?')) {
            try {
                await axios.delete(`http://localhost:8080/api/vendas/excluir/${id}`);
                setVendas(vendas.filter(venda => venda.id !== id));
            } catch (error) {
                console.error('Erro ao excluir venda:', error);
                alert('Erro ao excluir venda.');
            }
        }
    };

    return (
        <div className='containerlistavendas'>
            <Menu/>
            <h2>Lista de Vendas</h2>
            {/* Formulário de busca */}
            <form onSubmit={handleSearch}>
                <div>
                    <label>Vendedor:</label>
                    <input
                        type="text"
                        value={vendedor}
                        onChange={(e) => setVendedor(e.target.value)}
                        placeholder="Nome do vendedor"
                    />
                </div>
                <div>
                    <label>Data da Venda:</label>
                    <input
                        type="date"
                        value={dataVenda}
                        onChange={(e) => setDataVenda(e.target.value)}
                    />
                </div>
                <button type="submit">Buscar</button>
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
                                        {item.produtoNome} - Quantidade: {item.quantidade} - Valor Total:
                                        R${(item.quantidade * item.precoUnitario).toFixed(2)}
                                    </li>
                                ))}
                            </ul>
                        </td>
                        <td>R${venda.valorTotal.toFixed(2)}</td>
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
