export const Api = () => {};

export async function loadData(url, method, body, setSalons) {
  const response = await fetch(url, {
    method: method,
    credentials: "include",
    body: JSON.stringify({
      body,
    }),
    headers: {
      "Content-Type": "application/json",
    },
  });
  let json = [];
  if (response.ok) {
    json = await response.json();
  } else {
    alert("Nie znaleziono rekordow  w bazie danych dla 1 ");
  }
  setSalons(json);
}

export default Api;
