import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

const VendaList = () => {
    const [vendas, setVendas] = useState([]);
    const [vendedor, setVendedor] = useState('');

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
            const response = await axios.get('/api/vendas/listar', {
                params: { vendedor }
            });
            setVendas(response.data);
        } catch (error) {
            console.error('Erro ao buscar vendas:', error);
        }
    };

    return (
        <div>
            <h2>Lista de Vendas</h2>
            <form onSubmit={handleSearch}>
                <input
                    type="text"
                    placeholder="Buscar por vendedor"
                    value={vendedor}
                    onChange={(e) => setVendedor(e.target.value)}
                />
                <button type="submit">Buscar</button>
                <button type="button" onClick={fetchVendas}>Limpar</button>
            </form>
            <table border="1">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Data</th>
                        <th>Vendedor</th>
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
                            <td>{venda.valorTotal.toFixed(2)}</td>
                            <td>
                                <Link to={`/vendas/editar/${venda.id}`}>Editar</Link>
                                {' | '}
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
