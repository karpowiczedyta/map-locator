import { Input } from "tombac";
import { useState } from "react";
import { btUrl } from "/home/karpowic/Desktop/map-locator-all/map-locator-client/src/Constant.js";

export const EditBeautifingServiceType = ({ bs }) => {
  const [name, setName] = useState(null);
  const [price, setPrice] = useState(null);
  return (
    <>
      <h2>Enter the data,to edit them</h2>
      <div>
        <Input
          style={{ margin: "0 0 10px 0" }}
          onChange={(e) => setName(e.target.value)}
          placeholder="Enter name of service"
          width="300px"
          type="text"
          id="input-name"
        />
      </div>

      <div>
        <Input
          style={{ margin: "0 0 10px 0" }}
          onChange={(e) => setPrice(e.target.value)}
          placeholder="Enter price"
          width="300px"
          min="0"
          type="number"
          id="input-price"
        />
      </div>

      <button
        class="myButton"
        onClick={() => {
          console.log();
          load(name, price, bs.id);
        }}
      >
        Edit
      </button>
    </>
  );
};

async function load(name, price, bs) {
  const response = await fetch(`${btUrl}${bs}`, {
    method: "PUT",
    credentials: "include",
    body: JSON.stringify({
      id: 0,
      name: name,
      price: price,
      beautifyingServiceId: 0,
    }),
    headers: {
      "Content-Type": "application/json",
    },
  });

  document.getElementById("input-name").value = "";
  document.getElementById("input-price").value = "";
}

export default EditBeautifingServiceType;
