// ===== API BASE URL =====
const API = 'http://localhost:8080/api';

function getAuthHeaders() {
    const headers = { 'Content-Type': 'application/json' };
    let token = null;
    if (typeof getLoggedInUser === 'function') {
        const u = getLoggedInUser();
        if (u && u.token) token = u.token;
    }
    if (!token) {
        const raw = localStorage.getItem('bms_user');
        if (raw) {
            try {
                const parsed = JSON.parse(raw);
                token = parsed.token || (parsed.user && parsed.user.token);
            } catch (e) {}
        }
    }
    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }
    return headers;
}

// ===== GENERIC FETCH HELPERS =====
async function apiGet(endpoint) {
    const res = await fetch(`${API}${endpoint}`, {
        headers: getAuthHeaders()
    });
    if (!res.ok) {
        const err = await res.json().catch(() => ({ message: res.statusText }));
        throw new Error(err.message || 'Request failed');
    }
    return res.json();
}

async function apiPost(endpoint, data) {
    const res = await fetch(`${API}${endpoint}`, {
        method: 'POST',
        headers: getAuthHeaders(),
        body: JSON.stringify(data)
    });
    if (!res.ok) {
        const err = await res.json().catch(() => ({ message: res.statusText }));
        throw new Error(err.message || 'Request failed');
    }
    return res.json();
}

async function apiPut(endpoint, data) {
    const res = await fetch(`${API}${endpoint}`, {
        method: 'PUT',
        headers: getAuthHeaders(),
        body: data ? JSON.stringify(data) : undefined
    });
    if (!res.ok) {
        const err = await res.json().catch(() => ({ message: res.statusText }));
        throw new Error(err.message || 'Request failed');
    }
    return res.json();
}

async function apiDelete(endpoint) {
    const res = await fetch(`${API}${endpoint}`, {
        method: 'DELETE',
        headers: getAuthHeaders()
    });
    if (!res.ok) {
        const err = await res.json().catch(() => ({ message: res.statusText }));
        throw new Error(err.message || 'Request failed');
    }
    // might return text
    const text = await res.text();
    try { return JSON.parse(text); } catch { return text; }
}

// ===== USER APIs =====
const UserAPI = {
    register: (data) => apiPost('/auth/register', data),
    login: (data) => apiPost('/auth/login', data),
    getAll: () => apiGet('/users'),
    getById: (id) => apiGet(`/users/${id}`)
};


// ===== CITY APIs =====
const CityAPI = {
    add: (data) => apiPost('/cities', data),
    getAll: () => apiGet('/cities'),
    getById: (id) => apiGet(`/cities/${id}`)
};

// ===== MOVIE APIs =====
const MovieAPI = {
    add: (data) => apiPost('/movies', data),
    getAll: () => apiGet('/movies'),
    getById: (id) => apiGet(`/movies/${id}`),
    search: (title) => apiGet(`/movies/search?title=${encodeURIComponent(title)}`),
    getByGenre: (genre) => apiGet(`/movies/genre/${genre}`),
    getByLanguage: (lang) => apiGet(`/movies/language/${lang}`),
    update: (id, data) => apiPut(`/movies/${id}`, data),
    delete: (id) => apiDelete(`/movies/${id}`)
};

// ===== THEATER APIs =====
const TheaterAPI = {
    add: (data) => apiPost('/theaters', data),
    getAll: () => apiGet('/theaters'),
    getById: (id) => apiGet(`/theaters/${id}`),
    getByCity: (cityId) => apiGet(`/theaters/city/${cityId}`)
};

// ===== SCREEN APIs =====
const ScreenAPI = {
    add: (data) => apiPost('/screens', data),
    getAll: () => apiGet('/screens'),
    getById: (id) => apiGet(`/screens/${id}`),
    getByTheater: (theaterId) => apiGet(`/screens/theater/${theaterId}`)
};

// ===== SEAT APIs =====
const SeatAPI = {
    add: (data) => apiPost('/seats', data),
    getByScreen: (screenId) => apiGet(`/seats/screen/${screenId}`),
    getById: (id) => apiGet(`/seats/${id}`)
};

// ===== SHOW APIs =====
const ShowAPI = {
    add: (data) => apiPost('/shows', data),
    getAll: () => apiGet('/shows'),
    getById: (id) => apiGet(`/shows/${id}`),
    getByMovie: (movieId) => apiGet(`/shows/movie/${movieId}`),
    getByMovieAndDate: (movieId, date) => apiGet(`/shows/movie/${movieId}/date?date=${date}`)
};

// ===== BOOKING APIs =====
const BookingAPI = {
    create: (data) => apiPost('/bookings', data),
    getById: (id) => apiGet(`/bookings/${id}`),
    getByUser: (userId) => apiGet(`/bookings/user/${userId}`),
    cancel: (id) => apiPut(`/bookings/${id}/cancel`),
    getAvailableSeats: (showId) => apiGet(`/bookings/show/${showId}/available-seats`)
};