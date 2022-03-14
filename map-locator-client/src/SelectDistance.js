import { CreatableSelect } from "tombac";
import { Input } from "tombac";
import React, { useState } from "react";

export const SelectDistance = ({
  setDistance,
  setOptionToSearch,
  setTreatment,
  setPriceFrom,
  setPriceTo,
  setOpinion,
}) => {
  const [options, setOptions] = useState([
    { value: 0, label: "brak" },
    { value: 10, label: "10km" },
    { value: 20, label: "20km" },
    { value: 30, label: "30km" },
    { value: 40, label: "40km" },
    { value: 50, label: "50km" },
  ]);

  const [opinionOptions, setOpinionOptions] = useState([
    { opinionValue: 0.0, label: "brak" },
    { opinionValue: 3.0, label: "3.0" },
    { opinionValue: 3.5, label: "3.5" },
    { opinionValue: 4.0, label: "4.0" },
    { opinionValue: 4.5, label: "4.5" },
    { opinionValue: 5.0, label: "5.0" },
  ]);

  const [value, setValue] = useState(null);
  const [lastId, setLastId] = useState(2);

  const [opinionValue, setOpinionValue] = useState(null);
  const [opinionLastId, setOpinionLastId] = useState(2);

  if (value !== null) {
    setDistance(value.value);
  }

  if (opinionValue !== null) {
    setOpinion(opinionValue.opinionValue);
  }

  return (
    <>
      <div class="options">
        <label class="label">
          Wybierz w jakiej odleglosci powinny byc wyszukiwane dane:{" "}
        </label>
        <CreatableSelect
          $width="300px"
          class="select"
          onChange={setValue}
          onCreateOption={(label) => {
            const newOption = { value: lastId + 1, label };
            setOptions(() => [...options, newOption]);
            setLastId(lastId + 1);
            setValue(newOption);
          }}
          options={options}
          value={value}
        />
        <label class="label">
          Wybierz nazwe uslugi pod jaka chcesz szukac:{" "}
        </label>
        <Input
          onChange={(e) => setTreatment(e.target.value)}
          placeholder="Service name"
          width="300px"
          type="text"
        />
        <label class="label">Wybierz cene od: </label>
        <Input
          onChange={(e) => setPriceFrom(e.target.value)}
          placeholder="Enter the price"
          width="300px"
          type="number"
        />
        <label class="label">Wybierz cene do: </label>
        <Input
          onChange={(e) => setPriceTo(e.target.value)}
          placeholder="Enter the price"
          width="300px"
          type="number"
        />
        <label class="label">Wybierz opinie: </label>
        <CreatableSelect
          $width="300px"
          class="select"
          onChange={setOpinionValue}
          onCreateOption={(label) => {
            const newOption = { value: opinionLastId + 1, label };
            setOpinionOptions(() => [...opinionOptions, newOption]);
            setOpinionLastId(opinionLastId + 1);
            setValue(newOption);
          }}
          options={opinionOptions}
          value={opinionValue}
        />
        <button
          class="myButton search"
          onClick={() => {
            setOptionToSearch(1);
          }}
        >
          Szukaj
        </button>
        <button
          class="logout"
          onClick={() => {
            window.location = `http://test.move.tomtom.com/logout?redirect=${window.location}`;
          }}
        >
          Logout
        </button>
      </div>
    </>
  );
};
