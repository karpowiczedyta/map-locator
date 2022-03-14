import { Input } from "tombac";
import { useState } from "react";
import { btsUrl } from "/home/karpowic/Desktop/map-locator-all/map-locator-client/src/Constant.js";

export const EditBeautifingService = (salonId, setSalons) => {
  const [serviceType, setServiceType] = useState(null);
  const [salonName, setSalonName] = useState(null);
  const [town, setTown] = useState(null);
  const [latitude, setLatitude] = useState(0.0);
  const [longitude, setLongitude] = useState(0.0);

  return (
    <>
      <h2>Enter the data,to edit them</h2>
      <div>
        <Input
          style={{ margin: "0 0 10px 0" }}
          onChange={(e) => setServiceType(e.target.value)}
          placeholder="Enter type of service"
          width="300px"
          type="text"
          id="input-st"
        />
      </div>
      <div>
        <Input
          style={{ margin: "0 0 10px 0" }}
          onChange={(e) => setSalonName(e.target.value)}
          placeholder="Enter name of salon"
          width="300px"
          type="text"
          id="input-ns"
        />
      </div>
      <div>
        <Input
          style={{ margin: "0 0 10px 0" }}
          onChange={(e) => setTown(e.target.value)}
          placeholder="Enter town"
          width="300px"
          type="text"
          id="input-t"
        />
      </div>
      <div>
        <Input
          style={{ margin: "0 0 10px 0" }}
          onChange={(e) => setLatitude(e.target.value)}
          placeholder="Enter latitude"
          width="300px"
          type="number"
          id="input-lat"
        />
      </div>
      <div>
        <Input
          style={{ margin: "0 0 10px 0" }}
          onChange={(e) => setLongitude(e.target.value)}
          placeholder="Enter longitude"
          width="300px"
          type="number"
          id="input-lon"
        />
      </div>
      <button
        class="myButton"
        onClick={() => {
          console.log(salonId.salonId);
          load(
            serviceType,
            salonName,
            town,
            latitude,
            longitude,
            salonId.salonId,
            setSalons
          );
        }}
      >
        Edit
      </button>
    </>
  );
};

async function load(
  serviceType,
  salonName,
  town,
  latitude,
  longitude,
  salonId
) {
  const response = await fetch(`${btsUrl}${salonId}`, {
    method: "PUT",
    credentials: "include",
    body: JSON.stringify({
      id: 0,
      typeOfService: serviceType,
      nameOfSalon: salonName,
      town: town,
      lat: latitude,
      lon: longitude,
    }),
    headers: {
      "Content-Type": "application/json",
    },
  });
  document.getElementById("input-st").value = "";
  document.getElementById("input-ns").value = "";
  document.getElementById("input-t").value = "";
  document.getElementById("input-lat").value = "";
  document.getElementById("input-lon").value = "";
}

export default EditBeautifingService;
