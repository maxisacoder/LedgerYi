package cn.ledgeryi.framework.common.utils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.spongycastle.util.encoders.Hex;

@SuppressWarnings("serial")
public class DecodeResult implements Serializable {

  private int pos;
  private Object decoded;

  public DecodeResult(int pos, Object decoded) {
    this.pos = pos;
    this.decoded = decoded;
  }

  public int getPos() {
    return pos;
  }

  public Object getDecoded() {
    return decoded;
  }

  public String toString() {
    return asString(this.decoded);
  }

  private String asString(Object decoded) {
    if (decoded instanceof String) {
      return (String) decoded;
    } else if (decoded instanceof byte[]) {
      return Hex.toHexString((byte[]) decoded);
    } else if (decoded instanceof Object[]) {
      return Arrays.stream((Object[]) decoded)
          .map(this::asString)
          .collect(Collectors.joining());
    }
    throw new RuntimeException("Not a valid type. Should not occur");
  }
}
