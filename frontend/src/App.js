import React from 'react';
import {BrowserRouter as Router, Routes, Route, Link, useNavigate} from 'react-router-dom';
import Home from './components/Home/Home';
import ProdutoList from './components/ProdutoList/ProdutoList';
import FormProduto from './components/FormProduto/FormProduto';
import VendaList from './components/VendaList/VendaList';
import VendaForm from './components/VendaForm/VendaForm';
import EditarProduto from './components/EditarProduto/EditarProduto';
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
                <Route path="/produtos/editar/:id" element={<EditarProduto />} />
                <Route path="/vendas" element={<VendaList />} />
                <Route path="/vendas/nova" element={<VendaForm />} />
            </Routes>
        </Router>
    );
}

export default App;

