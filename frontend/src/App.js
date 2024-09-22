import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Home from './components/Home';
import ProdutoList from './components/ProdutoList';
import FormProduto from './components/FormProduto';
import VendaList from './components/VendaList';
import VendaForm from './components/VendaForm';

function App() {
  return (
    <Router>
      <nav>
        <ul>
          <li><Link to="/">Home</Link></li>
          <li><Link to="/produtos">Produtos</Link></li>
          <li><Link to="/produtos/nova">Novo Produto</Link></li>
          <li><Link to="/vendas">Vendas</Link></li>
          <li><Link to="/vendas/nova">Nova Venda</Link></li>
        </ul>
      </nav>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/produtos" element={<ProdutoList />} />
        <Route path="/produtos/nova" element={<FormProduto />} />
        <Route path="/vendas" element={<VendaList />} />
        <Route path="/vendas/nova" element={<VendaForm />} />
      </Routes>
    </Router>
  );
}

export default App;
