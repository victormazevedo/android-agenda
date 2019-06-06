package br.gov.sp.fatec.agenda;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {



    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Bundle bundle = this.getArguments();
        String enderecoAluno = bundle.getString("endereco");
        LatLng posicaoTeste = pegaCoordenadasDoEndereco(enderecoAluno);
        if (posicaoTeste != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(posicaoTeste, 17);
            googleMap.moveCamera(update);
            MarkerOptions marcador = new MarkerOptions();
            marcador.position(posicaoTeste);
            googleMap.addMarker(marcador);
        }
    }

    public LatLng pegaCoordenadasDoEndereco(String endereco) {
        try {
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> resultados =
                    geocoder.getFromLocationName(endereco, 1);
            if (!resultados.isEmpty()) {
                LatLng posicao = new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());
                return posicao;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
