import React, { useEffect, useState } from 'react';
import axios from 'axios';
import {Link, useNavigate} from 'react-router-dom';
import "./ProdutoList.css";
import Menu from "./Menu";

const ProdutoList = () => {


    const [produtos, setProdutos] = useState([
        {
            id:1,
            nome:"ma",
            descricao: "mnj",
            codigo: 123456,
            preco: 34,
            quantidade: 34,

        },
        {
            id:2,
            nome:"martyu",
            descricao: "mnfdsj",
            codigo: 123457,
            preco: 34,
            quantidade: 34,
        }
    ]);

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
            <Menu/>
            <div className='nucleo'>
                <h2>Lista de Produtos</h2>
                <div className="listaprodutos">
                    {produtos.map(produto => (
                        <div className="caixinha" key={produto.id}>
                            <p>{produto.id}</p>
                            <h3>{produto.nome}</h3>
                            <p><strong>Descrição:</strong> {produto.descricao}</p>
                            <p><strong>Código:</strong> {produto.codigo}</p>
                            <p><strong>Preço:</strong> R$ {produto.preco.toFixed(2)}</p>
                            <p><strong>Quantidade:</strong> {produto.quantidade}</p>
                            <div className="acoes">
                                <Link to={`/produtos/editar/${produto.id}`}>Editar</Link>
                                <button onClick={() => handleDelete(produto.id)}>Excluir</button>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
};

export default ProdutoList;
