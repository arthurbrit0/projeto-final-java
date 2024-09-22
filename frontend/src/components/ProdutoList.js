import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

const ProdutoList = () => {
    const [produtos, setProdutos] = useState([]);

    useEffect(() => {
        fetchProdutos();
    }, []);

    const fetchProdutos = async () => {
        try {
            const response = await axios.get('/api/produtos/listar');
            setProdutos(response.data);
        } catch (error) {
            console.error('Erro ao buscar produtos:', error);
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm('Tem certeza que deseja excluir este produto?')) {
            try {
                await axios.delete(`/api/produtos/excluir/${id}`);
                setProdutos(produtos.filter(produto => produto.id !== id));
            } catch (error) {
                console.error('Erro ao excluir produto:', error);
                alert('Erro ao excluir produto.');
            }
        }
    };

    return (
        <div>
            <h2>Lista de Produtos</h2>
            <table border="1">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Descrição</th>
                        <th>Código</th>
                        <th>Preço</th>
                        <th>Quantidade</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    {produtos.map(produto => (
                        <tr key={produto.id}>
                            <td>{produto.id}</td>
                            <td>{produto.nome}</td>
                            <td>{produto.descricao}</td>
                            <td>{produto.codigo}</td>
                            <td>{produto.preco.toFixed(2)}</td>
                            <td>{produto.quantidade}</td>
                            <td>
                                <Link to={`/produtos/editar/${produto.id}`}>Editar</Link>
                                {' | '}
                                <button onClick={() => handleDelete(produto.id)}>Excluir</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default ProdutoList;
