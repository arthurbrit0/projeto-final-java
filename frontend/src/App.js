import React from 'react';
import {BrowserRouter as Router, Routes, Route, Link, useNavigate} from 'react-router-dom';
import Home from './components/Home';
import ProdutoList from './components/ProdutoList';
import FormProduto from './components/FormProduto';
import VendaList from './components/VendaList';
import VendaForm from './components/VendaForm';
import EditarProduto from './components/EditarProduto'; // Novo componente de edição de produto
import './App.css'
function App() {
    return (
        <Router>
            <nav  className= 'fixo'>
                <h1 className='titulo'>Estante Digital</h1>

            </nav>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/produtos" element={<ProdutoList />} />
                <Route path="/produtos/nova" element={<FormProduto />} />
                <Route path="/produtos/editar/:id" element={<EditarProduto />} /> {/* Rota para editar produto */}
                <Route path="/vendas" element={<VendaList />} />
                <Route path="/vendas/nova" element={<VendaForm />} />
            </Routes>
        </Router>
    );
}

export default App;


/*<
li className='botao'><Link to="/">Home</Link></li>
                    <button className='botao'><Link to="/produtos">Produtos</Link></button>
                    <button className='botao'><Link to="/produtos/nova">Novo Produto</Link></button>
                    <button className='botao'><Link to="/vendas">Vendas</Link></button>
                    <button className='botao'><Link to="/vendas/nova">Nova Venda</Link></button>
                    */
