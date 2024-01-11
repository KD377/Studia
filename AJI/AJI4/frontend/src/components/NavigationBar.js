import React from 'react';
import { Navbar, Nav } from 'react-bootstrap';
import { NavLink, Link } from 'react-router-dom';

const NavigationBar = () => {
  return (
    <Navbar bg="dark" expand="lg" className='px-5 py-3'>
      <Link to="/" className='navbar-brand text-white'>
        Strona Główna
      </Link>
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="mr-auto">
          <NavLink to="/manage" className='nav-link text-white'>
            Manage
          </NavLink>
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
};

export default NavigationBar;
