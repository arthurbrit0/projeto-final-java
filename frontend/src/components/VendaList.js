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
    const handleClear = () => {
        setVendedor('');  // Limpa o campo vendedor
        setDataVenda('');  // Limpa o campo data
        fetchVendas();     // Recarrega todas as vendas
    };

    return (
        <div className='containerlistavendas'>
            <Menu/>
            <div className='site'>
                {/* Formulário de busca */}
                <form className="busca" onSubmit={handleSearch}>
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
                    <div className='botoes'>
                        <button className='buscar' type="submit">Buscar</button>
                        <button className='limpar' type="button" onClick={handleClear}>Limpar</button>
                    </div>
                </form>

                <div className="listavendas">
                    <h2>Lista de Vendas</h2>
                    {vendas.map((venda) => (
                        <div key={venda.id} className="venda">
                            <div className="vendaid">
                                <h3>Venda #{venda.id}</h3>
                                <span className="vendadata">{new Date(venda.data).toLocaleString()}</span>
                            </div>
                            <div className="cartao">
                                <p><strong>Vendedor:</strong> {venda.vendedor}</p>
                                <p><strong>Produtos Comprados:</strong></p>
                                <ul>
                                    {venda.itens.map((item) => (
                                        <li key={item.id}>
                                            {item.produtoNome} - Quantidade: {item.quantidade} - Valor Total:
                                            R${(item.quantidade * item.precoUnitario).toFixed(2)}
                                        </li>
                                    ))}
                                </ul>
                            </div>
                            <div className="borda">
                                <p><strong>Valor Total:</strong> R${venda.valorTotal.toFixed(2)}</p>
                                <button className="excluir" onClick={() => handleDelete(venda.id)}>Excluir</button>
                            </div>
                        </div>
                    ))}
                </div>

            </div>
        </div>
    );
};

export default VendaList;
