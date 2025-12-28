-- Property Management System Database Schema
-- SQLite

-- Properties table
CREATE TABLE IF NOT EXISTS properties (
    property_id INTEGER PRIMARY KEY AUTOINCREMENT,
    address TEXT NOT NULL,
    property_type TEXT NOT NULL CHECK(property_type IN ('Residential', 'Commercial')),
    owner_id INTEGER NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Units table
CREATE TABLE IF NOT EXISTS units (
    unit_id INTEGER PRIMARY KEY AUTOINCREMENT,
    property_id INTEGER NOT NULL,
    unit_number TEXT NOT NULL,
    rental_price REAL NOT NULL,
    area REAL,
    status TEXT NOT NULL DEFAULT 'Vacant' CHECK(status IN ('Vacant', 'Occupied', 'Maintenance')),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (property_id) REFERENCES properties(property_id)
);

-- Tenants table
CREATE TABLE IF NOT EXISTS tenants (
    tenant_id INTEGER PRIMARY KEY AUTOINCREMENT,
    full_name TEXT NOT NULL,
    contact_info TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Leases table
CREATE TABLE IF NOT EXISTS leases (
    lease_id INTEGER PRIMARY KEY AUTOINCREMENT,
    tenant_id INTEGER NOT NULL,
    unit_id INTEGER NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    rent_amount REAL NOT NULL,
    status TEXT NOT NULL DEFAULT 'Active' CHECK(status IN ('Active', 'Expired', 'Terminated')),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (tenant_id) REFERENCES tenants(tenant_id),
    FOREIGN KEY (unit_id) REFERENCES units(unit_id)
);

-- Payments table
CREATE TABLE IF NOT EXISTS payments (
    payment_id INTEGER PRIMARY KEY AUTOINCREMENT,
    lease_id INTEGER NOT NULL,
    amount REAL NOT NULL,
    payment_date DATE NOT NULL,
    payment_method TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (lease_id) REFERENCES leases(lease_id)
);

-- Expenses table
CREATE TABLE IF NOT EXISTS expenses (
    expense_id INTEGER PRIMARY KEY AUTOINCREMENT,
    property_id INTEGER NOT NULL,
    amount REAL NOT NULL,
    category TEXT NOT NULL,
    expense_date DATE NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (property_id) REFERENCES properties(property_id)
);

-- Maintenance Requests table
CREATE TABLE IF NOT EXISTS maintenance_requests (
    request_id INTEGER PRIMARY KEY AUTOINCREMENT,
    unit_id INTEGER NOT NULL,
    issue_description TEXT NOT NULL,
    request_date DATE NOT NULL,
    status TEXT NOT NULL DEFAULT 'New' CHECK(status IN ('New', 'In Progress', 'Completed')),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (unit_id) REFERENCES units(unit_id)
);
