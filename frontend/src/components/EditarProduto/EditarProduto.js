import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import Menu from "../Menu/Menu";
import './EditarProduto.css'

function EditarProduto() {
    const { id } = useParams(); // Obtenha o ID da URL
    const [produto, setProduto] = useState(null);
    const navigate = useNavigate(); // Hook para redirecionamento

    useEffect(() => {
        // Carregar os dados do produto para edição
        fetch(`http://localhost:8080/api/produtos/buscar/${id}`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`Erro ao carregar o produto. Status: ${response.status}`);
                }
                return response.json();
            })
            .then((data) => setProduto(data))
            .catch((error) => {
                console.error('Erro ao carregar o produto:', error);
                // Você pode exibir uma mensagem de erro ao usuário aqui
            });
    }, [id]);

    const handleSubmit = (e) => {
        e.preventDefault();
        // Chame a API para atualizar o produto
        fetch(`http://localhost:8080/api/produtos/atualizar/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(produto),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Erro ao atualizar o produto. Status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                console.log("Produto atualizado:", data);
                navigate("/produtos");
            })
            .catch((error) => {
                console.error('Erro ao atualizar o produto:', error);
                // Trate o erro aqui, exiba uma mensagem ao usuário, etc.
            });
    };

    if (!produto) return <div>Carregando...</div>;

    return (
        <div className='containerEditar'>
            <Menu />
            <div className='editor'>
                <h2>Editar Produto</h2>
                <form onSubmit={handleSubmit}>

                    <label>Nome:</label>
                    <input
                        type="text"
                        value={produto.nome}
                        onChange={(e) => setProduto({...produto, nome: e.target.value})}
                    />

                    <label>Descrição:</label>
                    <input
                        type="text"
                        value={produto.descricao}
                        onChange={(e) => setProduto({...produto, descricao: e.target.value})}
                    />

                    <label>Preço:</label>
                    <input
                        type="number"
                        step="0.01"
                        value={produto.preco}
                        onChange={(e) => setProduto({...produto, preco: parseFloat(e.target.value)})}
                    />

                    <label>Quantidade:</label>
                    <input
                        type="number"
                        value={produto.quantidade}
                        onChange={(e) => setProduto({...produto, quantidade: parseInt(e.target.value)})}
                    />

                    <button type="submit">Salvar</button>
                </form>
            </div>
        </div>
    );
}

export default EditarProduto;
