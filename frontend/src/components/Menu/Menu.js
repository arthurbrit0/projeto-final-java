import React from "react";
import {useNavigate} from "react-router-dom";
import './Menu.css'

const Menu = () => {
    const navigate = useNavigate();    //importação da função de mudança de página
    const home = () => { navigate('/'); }
    const listaprodutos = () => { navigate('/produtos'); }
    const novoproduto = () => { navigate('/produtos/nova'); }
    const listavendas = () => { navigate('/vendas'); }
    const novavenda = () => { navigate('/vendas/nova'); }
    return(
        <div className='menu'>
            <button className='botao' onClick={home}>Home</button>
            <button className='botao' onClick={listaprodutos}>Produtos</button>
            <button className='botao' onClick={novoproduto}>Novo Produto</button>
            <button className='botao' onClick={listavendas}>Vendas</button>
            <button className='botao' onClick={novavenda}>Nova Venda</button>
        </div>
    )
};
export default Menu;