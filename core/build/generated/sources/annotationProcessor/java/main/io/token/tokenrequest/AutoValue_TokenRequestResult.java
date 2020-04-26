

package io.token.tokenrequest;

import io.token.proto.common.security.SecurityProtos;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
final class AutoValue_TokenRequestResult extends TokenRequestResult {

  private final String tokenId;

  private final SecurityProtos.Signature signature;

  AutoValue_TokenRequestResult(
      String tokenId,
      SecurityProtos.Signature signature) {
    if (tokenId == null) {
      throw new NullPointerException("Null tokenId");
    }
    this.tokenId = tokenId;
    if (signature == null) {
      throw new NullPointerException("Null signature");
    }
    this.signature = signature;
  }

  @Override
  public String getTokenId() {
    return tokenId;
  }

  @Override
  public SecurityProtos.Signature getSignature() {
    return signature;
  }

  @Override
  public String toString() {
    return "TokenRequestResult{"
         + "tokenId=" + tokenId + ", "
         + "signature=" + signature
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof TokenRequestResult) {
      TokenRequestResult that = (TokenRequestResult) o;
      return (this.tokenId.equals(that.getTokenId()))
           && (this.signature.equals(that.getSignature()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= tokenId.hashCode();
    h$ *= 1000003;
    h$ ^= signature.hashCode();
    return h$;
  }

}
