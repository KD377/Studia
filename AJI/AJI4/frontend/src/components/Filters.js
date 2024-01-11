import React from 'react';

const Filters = ({ filterName, setFilterName, filterCategory, setFilterCategory, categories }) => {
  return (
    <div className="filters mb-3">
      <div className='row'>
        <div className='col-md-6'>
          <label>
            Filter by Name:
            <input
              type="text"
              value={filterName}
              onChange={(e) => setFilterName(e.target.value)}
            />
          </label>
        </div>
        <div className='col-md-6'>
          <label>
            Filter by Category:
            <select
              value={filterCategory}
              onChange={(e) => setFilterCategory(e.target.value)}
            >
              {categories.map((category) => (
                <option key={category} value={category}>{category}</option>
              ))}
            </select>
          </label>
        </div>
      </div>
    </div>
  );
};

export default Filters;
