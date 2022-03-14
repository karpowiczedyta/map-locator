import Marker from "vector-maps/dist/features/Marker";

export const DrawPoint = ({ salon, setOpen }) => {
  return (
    <>
      <Marker
        key={salon.properties.id}
        lng={salon.geometry.coordinates[0]}
        lat={salon.geometry.coordinates[1]}
        onMouseLeave={(e) => console.log()}
        onClick={(e) => {
          e.originalEvent.stopPropagation();
          setOpen(true);
        }}
      />
    </>
  );
};
