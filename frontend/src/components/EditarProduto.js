import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

function EditarProduto() {
    const { id } = useParams(); // Obtenha o ID da URL
    const [produto, setProduto] = useState(null);
    const navigate = useNavigate(); // Hook para redirecionamento

    useEffect(() => {
        // Carregar os dados do produto para edição
        fetch(`/api/produtos/buscar/${id}`)
            .then((response) => response.json())
            .then((data) => setProduto(data));
    }, [id]);

    const handleSubmit = (e) => {
        e.preventDefault();
        // Chame a API para atualizar o produto
        fetch(`/api/produtos/atualizar/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(produto),
        })
            .then(response => response.json())
            .then(data => {
                // Tratar o resultado da atualização e redirecionar
                console.log("Produto atualizado:", data);
                // Redirecionar para a lista de produtos
                navigate("/produtos");
            });
    };

    if (!produto) return <div>Carregando...</div>;

    return (
        <div>
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
                    value={produto.preco}
                    onChange={(e) => setProduto({...produto, preco: e.target.value})}
                />

                {/* Campo para editar a quantidade */}
                <label>Quantidade:</label>
                <input
                    type="number"
                    value={produto.quantidade}
                    onChange={(e) => setProduto({...produto, quantidade: e.target.value})}
                />

                <button type="submit">Salvar</button>
            </form>
        </div>
    );
}

export default EditarProduto;
